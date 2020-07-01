package newsApp.services.scraperService;


import lombok.SneakyThrows;
import newsApp.models.scraperModel.ScrapData;
import newsApp.models.scraperModel.ScraperSkeleton;
import newsApp.utils.webScraperUtils.NewsSitesSkeleton;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiFunction;


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

    private <S,R> List<R> fold(Map<S,S> data , BiFunction<S,S,R> f){
        List<R> result = new ArrayList<>();
        data.entrySet().stream().parallel().forEach(url->{
            try {
                result.add(f.apply(url.getKey(),url.getValue()));
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


         BiFunction<String, String, ScrapData> f = new BiFunction<String, String, ScrapData>() {
             @SneakyThrows
             @Override
             public ScrapData apply(String url, String path) {
                 return new ScrapData(Jsoup.connect(url).get(),path);
             }
         };


         throw new RuntimeException("ScraperService => scrap()");
     }

    private void getNews(List<ScrapData> fold) {
        fold.stream().map(s -> s.getDocument().select(s.getPathToNews()))
                .forEach(System.out::println);
    }
}
