package newsApp.services.scraperService;


import lombok.SneakyThrows;
import newsApp.models.scraperModel.ScrapData;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiFunction;


@Service
public class ScraperService {


    private final Map<String, String> data = new HashMap<String,String>(){{
        put("https://en.trend.az/","path #to .card");
        put("https://edition.cnn.com/","path #to .card");
        put("https://news.az/","path #to .card");
        put("https://www.huffpost.com/","path #to .card");
        put("https://www.nytimes.com/","path #to .card");
        put("https://www.foxnews.com/","path #to .card");
        put("https://www.washingtonpost.com/","path #to .card");
        put("https://www.kyivpost.com/","path #to .card");
        put("https://www.bbc.com/news","path #to .card");
    }};

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
         BiFunction<String, String, ScrapData> f = new BiFunction<String, String, ScrapData>() {
             @SneakyThrows
             @Override
             public ScrapData apply(String url, String path) {
                 return new ScrapData(Jsoup.connect(url).get(),path);
             }
         };
         List<ScrapData> fold = fold(
                 data,
                 f
         );

         getNews(fold);

         throw new RuntimeException("ScraperService => scrap()");
     }

    private void getNews(List<ScrapData> fold) {
        fold.stream().map(s -> s.getDocument().select(s.getPathToNews()))
                .forEach(System.out::println);
    }
}
