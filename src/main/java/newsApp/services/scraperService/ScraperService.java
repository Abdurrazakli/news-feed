package newsApp.services.scraperService;


import lombok.SneakyThrows;
import newsApp.models.scraperModel.DocumentAndSkeleton;
import newsApp.models.scraperModel.ScraperSkeleton;
import newsApp.utils.webScraperUtils.NewsSitesSkeleton;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class ScraperService {


    /**
     * generic helper functions
     *
     */
    private <T> List<T> toList(Set<T> keySet) {
        return new ArrayList<>(keySet);
    }

    /**
     * generic main functions
     *
     */

    private <S,R> List<R> fold(List<S> data , Function<S,R> f){
        List<R> result = new ArrayList<>();
        data.stream().parallel().forEach(element->{
            try {
                result.add(f.apply(element));
            }catch (Exception ignored){ }
        });
        return result;
    }

    /**
     *  parent function
     */
     public void scrap(){

         // News Skeleton Data
         List<ScraperSkeleton> scraperSkeletons = NewsSitesSkeleton.get();


         Function<ScraperSkeleton, DocumentAndSkeleton> f = new Function<ScraperSkeleton, DocumentAndSkeleton>() {
             @SneakyThrows
             @Override
             public DocumentAndSkeleton apply(ScraperSkeleton skeleton) {
                 return new DocumentAndSkeleton(Jsoup.connect(skeleton.getWebSite()).get(),skeleton);
             }
         };

         List<DocumentAndSkeleton> folded = fold(
                 scraperSkeletons,
                 f
         );

         getNews(folded);


         throw new RuntimeException("ScraperService => scrap()");
     }

    private void getNews(List<DocumentAndSkeleton> folded) {
         folded.stream().flatMap(o->
                 getSection(o).flatMap(sec ->
                         getContent(o,sec).map(content->{
                             String title = sec.select(o.getSkeleton().getPathToTitle()).attr("text");  // text?? look back, add attr name too;
                             throw new IllegalArgumentException("Not finished");
                         }))
         )

         .collect(Collectors.toList()); // not sure about data type? might be HashMap

    }

    private Stream<Element> getSection(DocumentAndSkeleton documentAndSkeleton){
         return documentAndSkeleton.getDocument().select(documentAndSkeleton.getSkeleton().getPathToSection()).stream();
    }

    private Stream<Element> getContent(DocumentAndSkeleton documentAndSkeleton,Element element){
         return element.select(documentAndSkeleton.getSkeleton().getPathToContent()).stream();
    }

//   new ScraperSkeleton("https://www.foxnews.com/world","body #wrapper .page .page-content .row .main-content > section",
//   ".content > article",".m > a > img",".info > header .title > a",".info > header .title > a")


}
