package newsApp.services.scraperService;


import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import newsApp.exceptions.DomainNotExists;
import newsApp.models.newsModel.DetailedNews;
import newsApp.models.newsModel.Domain;
import newsApp.models.newsModel.News;
import newsApp.models.scraperModel.DocumentAndSkeleton;
import newsApp.models.scraperModel.NewsScraperSkeleton;
import newsApp.repo.newsRepo.DetailedNewsRepo;
import newsApp.repo.newsRepo.DomainRepo;
import newsApp.repo.newsRepo.NewsRepo;
import newsApp.utils.webScraperUtils.NewsSitesStructures;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.dao.DataIntegrityViolationException;
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
    private final DetailedNewsRepo detailedNewsRepo;
    private final DomainRepo domainRepo;
    private final List<NewsScraperSkeleton> scraperSkeletons = NewsSitesStructures.getNewsSkeleton();


    public ScraperService(DetailedNewsRepo detailedNewsRepo, NewsRepo newsRepo, DomainRepo domainRepo) {
        this.detailedNewsRepo = detailedNewsRepo;
        this.domainRepo = domainRepo;
    }

    /**
     *  parent function
     */
    public List<DetailedNews> scrap(){
        writeNewDomainsToDb(scraperSkeletons);

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

        List<News> newNews = getNews(folded);

        log.info("count of new news: "+newNews.size());

        //get the content
        List<DetailedNews> detailedNews = getDetailedNews(newNews);

        //save to db
        saveNewsToDb(detailedNews);

        //for test purposes return news
        return detailedNews;   //TODO make scrape() void when all things straightforward;
    }



    /**
     * main Functions
     */
    private List<News> getNews(List<DocumentAndSkeleton<NewsScraperSkeleton>> folded) { // list of news; link=>content; Map<String, DetailedNews> data???
        return folded.stream().parallel().flatMap(o ->
                {

                    Domain domain = domainRepo.findByDomain(o.getSkeleton().getDomain().getDomain()).orElseThrow(DomainNotExists::new);
                    return StreamOfSection(o).parallel().flatMap(sec ->
                        StreamOfContent(o, sec).parallel().map(content -> {
                            try {
                                String title = content.select(o.getSkeleton().getPathToTitle()).text();  // text?? look back, add attr name too;
                                String rowImg = content.select(o.getSkeleton().getPathToImg()).attr(o.getSkeleton().getImageAttr());
                                String image = formatData(rowImg,o.getSkeleton().getDomain().getDomain());
                                String rowNewsLink = content.select(o.getSkeleton().getPathToNewsLink()).attr("href");
                                String newsLink = formatData(rowNewsLink,o.getSkeleton().getDomain().getDomain());
                                return new News(title, newsLink, o.getSkeleton().getAddress(),domain, image, LocalDateTime.now());
                            } catch (Exception ignored) {
                                log.error("Site content exception!");
                            }
                            return new News("","","",domain,"",LocalDateTime.now());
                        }));}
        )
                .filter(News::isNull)
                .collect(Collectors.toList());
    }

    private List<DetailedNews> getDetailedNews(List<News> news) { // newsLink : news;
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

        // Document and skeleton of site and websiteSkeleton(for paragraph path);
        return fold.stream().parallel().map(nds->
            new DetailedNews(/*news*/nds.getSkeleton(), /* content */StreamOfParagraphs(nds,scraperSkeletons)
                    .map(Element::text)
                    .collect(Collectors.joining("\n</br></br>")))
        )
        .collect(Collectors.toList());

    }

    private void saveNewsToDb(List<DetailedNews> news) {
        news.forEach(n->{
            try {
                detailedNewsRepo.save(n);
            }catch (DataIntegrityViolationException ignored){
                log.error("Data integrity error!");
            }
        });
    }

    private void writeNewDomainsToDb(List<NewsScraperSkeleton> scraperSkeletons) {

        List<Domain> newDomains = scraperSkeletons.stream()
                .map(NewsScraperSkeleton::getDomain)
                .collect(Collectors.toList());

        newDomains.forEach(d->{
            try {
                domainRepo.save(d);
            }catch (DataIntegrityViolationException ignored){
                log.info("Data integrity error! Domains");
            }
        });
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

    private Stream<Element> StreamOfParagraphs(DocumentAndSkeleton<News> nds, List<NewsScraperSkeleton> newsSkeleton) {
        return nds.getDocument().select(findParagraphPathOfSite(nds,newsSkeleton)).stream();
    }

    private String findParagraphPathOfSite(DocumentAndSkeleton<News> nds, List<NewsScraperSkeleton> newsSkeleton) {
        return newsSkeleton.stream().filter(ns -> ns.getAddress().equals(nds.getSkeleton().getSource())).findFirst().orElseThrow(RuntimeException::new).getPathToContent();

    }

    private Stream<Element> StreamOfSection(DocumentAndSkeleton<NewsScraperSkeleton> documentAndSkeleton){
         return documentAndSkeleton.getDocument().select(documentAndSkeleton.getSkeleton().getPathToSection()).stream();
    }

    private Stream<Element> StreamOfContent(DocumentAndSkeleton<NewsScraperSkeleton> documentAndSkeleton, Element element){
         return element.select(documentAndSkeleton.getSkeleton().getPathToCard()).stream();
    }

    private String formatData(String rowData, String domain) {
        return rowData.startsWith("http") ? rowData : domain.concat(rowData);
    }

}
