package newsApp.services.scraperService;


import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import newsApp.models.newsModel.DetailedNews;
import newsApp.models.newsModel.News;
import newsApp.models.scraperModel.DocumentAndSkeleton;
import newsApp.models.scraperModel.NewsScraperSkeleton;
import newsApp.utils.webScraperUtils.NewsSitesSkeleton;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
@Service
public class ScraperService {
    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML," +
            "like Gecko) Chrome/51.0.2704.103 Safari/537.36";

    /**
     *  parent function
     */
    public Collection<DetailedNews> scrap(){
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

        HashMap<String, News> news = getNews(folded);

//        filterInDbNews(news);

        List<DetailedNews> detailedNews = getDetailedNews(news);

        return detailedNews;

//        throw new IllegalArgumentException("main method in ScrapService: Not implemented yet");
    }

    private HashMap<String, News> filterInDbNews(HashMap<String, News> news) {
        throw new IllegalArgumentException("Not impl yet");
    }


    /**
     * main Functions
     */
    private HashMap<String, News> getNews(List<DocumentAndSkeleton<NewsScraperSkeleton>> folded) { // list of news; link=>content; Map<String, DetailedNews> data???
        return folded.stream().parallel().flatMap(o ->
                StreamOfSection(o).flatMap(sec ->
                        StreamOfContent(o, sec).map(content -> {
                            try {
                                String title = content.select(o.getSkeleton().getPathToTitle()).text();  // text?? look back, add attr name too;
                                String rowImg = content.select(o.getSkeleton().getPathToImg()).attr(o.getSkeleton().getImageAttr());
                                String image = formatData(rowImg,o.getSkeleton().getDomain());
                                String rowNewsLink = content.select(o.getSkeleton().getPathToNewsLink()).attr("href");
                                String newsLink = formatData(rowNewsLink,o.getSkeleton().getDomain());

                                return new News(UUID.randomUUID(), title, newsLink, o.getSkeleton().getAddress(), image, LocalDateTime.now());
                            } catch (Exception ignored) {
                                log.error("Site content exception!");
                            }
                            return new News(UUID.randomUUID(),"","","","",LocalDateTime.now());
                        }))
        )
                .filter(News::isNull)
                .collect(Collectors.toMap(News::getNewsLink,Function.identity() , (s, s2) -> s2, HashMap::new));
    }

    private List<DetailedNews> getDetailedNews(HashMap<String, News> news) { // newsLink : news;

        HashMap<String, String> newsParagraphPath = NewsSitesSkeleton.getNewsParagraphPath();

        Function<News, DocumentAndSkeleton<News>> f = new Function<News, DocumentAndSkeleton<News>>() {
            @SneakyThrows
            @Override
            public DocumentAndSkeleton<News> apply(News n) {
                return new DocumentAndSkeleton<>(Jsoup.connect(n.getNewsLink()).userAgent(USER_AGENT).get(), n);
            }
        };
        List<DocumentAndSkeleton<News>> fold = fold(
                new ArrayList<>(news.values()),
                f
        );

        return fold.stream().parallel().map(nds->
            new DetailedNews(UUID.randomUUID(),nds.getSkeleton(),StreamOfParagraphs(nds,newsParagraphPath).map(Element::text).collect(Collectors.joining("\n")))
        )
        .collect(Collectors.toList());
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
