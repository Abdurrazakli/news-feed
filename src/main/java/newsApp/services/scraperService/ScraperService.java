package newsApp.services.scraperService;


import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import newsApp.models.newsModel.DetailedNews;
import newsApp.models.newsModel.News;
import newsApp.models.scraperModel.DocumentAndSkeleton;
import newsApp.models.scraperModel.NewsScraperSkeleton;
import newsApp.repo.newsRepo.DetailedNewsRepo;
import newsApp.repo.newsRepo.NewsRepo;
import newsApp.utils.webScraperUtils.NewsSitesSkeleton;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
@Service
public class ScraperService {
    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML," +
            "like Gecko) Chrome/51.0.2704.103 Safari/537.36";

    private final DetailedNewsRepo detailedNewsRepo;
    private final NewsRepo newsRepo;

    public ScraperService(DetailedNewsRepo detailedNewsRepo, NewsRepo newsRepo) {
        this.detailedNewsRepo = detailedNewsRepo;
        this.newsRepo = newsRepo;
    }

    /**
     *  parent function
     */
    public List<DetailedNews> scrap(){
        List<NewsScraperSkeleton> scraperSkeletons = NewsSitesSkeleton.getNewsSkeleton();

        Function<NewsScraperSkeleton, DocumentAndSkeleton<NewsScraperSkeleton>> f = new Function<NewsScraperSkeleton, DocumentAndSkeleton<NewsScraperSkeleton>>() {
            @SneakyThrows
            @Override
            public DocumentAndSkeleton<NewsScraperSkeleton> apply(NewsScraperSkeleton skeleton) {
                return new DocumentAndSkeleton<>(Jsoup.connect(skeleton.getAddress()).userAgent(USER_AGENT).get(), skeleton);
            }
        };

        List<DocumentAndSkeleton<NewsScraperSkeleton>> folded = fold(
                scraperSkeletons,
                f
        );

        List<News> news = getNews(folded);
//        List<News> newNews = filterWithInDbNews(news);
        List<DetailedNews> detailedNews = getDetailedNews(news);
        saveNewsToDb(detailedNews);
        return detailedNews;
    }

    private void writeToDb(){
        List<DetailedNews> data = scrap();
        saveNewsToDb(data);
    }


    /**
     * main Functions
     */
    private List<News> getNews(List<DocumentAndSkeleton<NewsScraperSkeleton>> folded) { // list of news; link=>content; Map<String, DetailedNews> data???
        return folded.stream().parallel().flatMap(o ->
                StreamOfSection(o).flatMap(sec ->
                        StreamOfContent(o, sec).map(content -> {
                            try {
                                String title = content.select(o.getSkeleton().getPathToTitle()).text();  // text?? look back, add attr name too;
                                String rowImg = content.select(o.getSkeleton().getPathToImg()).attr(o.getSkeleton().getImageAttr());
                                String image = formatData(rowImg,o.getSkeleton().getDomain());
                                String rowNewsLink = content.select(o.getSkeleton().getPathToNewsLink()).attr("href");
                                String newsLink = formatData(rowNewsLink,o.getSkeleton().getDomain());

                                return new News(title, newsLink, o.getSkeleton().getAddress(), image, LocalDateTime.now());
                            } catch (Exception ignored) {
                                log.error("Site content exception!");
                            }
                            return new News("","","","",LocalDateTime.now());
                        }))
        )
                .filter(News::isNull)
                .collect(Collectors.toList());
    }

    private List<DetailedNews> getDetailedNews(List<News> news) { // newsLink : news;

        HashMap<String, String> newsParagraphPath = NewsSitesSkeleton.getNewsParagraphPath();

        Function<News, DocumentAndSkeleton<News>> f = new Function<News, DocumentAndSkeleton<News>>() {
            @SneakyThrows
            @Override
            public DocumentAndSkeleton<News> apply(News n) {
                return new DocumentAndSkeleton<>(Jsoup.connect(n.getNewsLink()).userAgent(USER_AGENT).get(), n);
            }
        };
        List<DocumentAndSkeleton<News>> fold = fold(
                news,
                f
        );

        return fold.stream().parallel().map(nds->
            new DetailedNews(nds.getSkeleton(),(String)StreamOfParagraphs(nds,newsParagraphPath).map(Element::text).collect(Collectors.joining("\n")))
        )
        .collect(Collectors.toList());
    }

    private void saveNewsToDb(List<DetailedNews> news) {
        detailedNewsRepo.saveAll(news);
    }

    /**
     * generic helper function
     *
     */
    private <S,R> List<R> fold(List<S> data , Function<S,R> f){
        List<R> result = new ArrayList<>();
        data.stream().parallel().forEach(element->{
            try {
                result.add(f.apply(element));
            }catch (Exception ignored){
                log.error("Get Document exception");
            }
        });
        return result;
    }


    /**
     * Helper Functions
     */

    private List<News> filterWithInDbNews(List<News> fetchedNews) {
        Page<News> newsFromDb = newsRepo.findAll(PageRequest.of(0, 100));

        if (ChronoUnit.HOURS.between(newsFromDb.getContent().get(1).getPublishedDate(),LocalDateTime.now()) >= 4) return Collections.emptyList();

        return newsFromDb.stream().flatMap(fromDb ->
                fetchedNews.stream().filter(fromWeb -> !fromWeb.getNewsLink().equals(fromDb.getNewsLink())))
                .collect(Collectors.toList());
    }

    private Stream<Element> StreamOfParagraphs(DocumentAndSkeleton<News> nds, HashMap<String, String> newsParagraphPath) {
        return nds.getDocument().select(newsParagraphPath.get(nds.getSkeleton().getSource())).stream();
    }

    private Stream<Element> StreamOfSection(DocumentAndSkeleton<NewsScraperSkeleton> documentAndSkeleton){
         return documentAndSkeleton.getDocument().select(documentAndSkeleton.getSkeleton().getPathToSection()).stream();
    }

    private Stream<Element> StreamOfContent(DocumentAndSkeleton<NewsScraperSkeleton> documentAndSkeleton, Element element){
         return element.select(documentAndSkeleton.getSkeleton().getPathToContent()).stream();
    }

    private String formatData(String rowData, String domain) {
        return rowData.startsWith("http") ? rowData : domain.concat(rowData);
    }




}
