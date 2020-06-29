package app.webScraper.service;


import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;


@Service
public class ScraperService {
    private final List<String> newsSites = new ArrayList<>(Arrays.asList("www.bbc.com/news/world"));

    private <A,T> List<A> fold(List<T> data , Function<T,A> f){
        List<A> doc = new ArrayList<>();
        data.stream().parallel().forEach(url->{
            try {
                doc.add(f.apply(url));
            }catch (Exception ignored){ }
        });
        return doc;
    }


     public void scrap(){
         Function<String, Document> f = new Function<String, Document>() {
             @SneakyThrows
             @Override
             public Document apply(String url){
                 return Jsoup.connect(url).get();
             }
         };
         List<Document> listOfDocuments = fold(
                 newsSites,
                 f
         );

         throw new RuntimeException("ScraperService => scrap()");

     }


}
