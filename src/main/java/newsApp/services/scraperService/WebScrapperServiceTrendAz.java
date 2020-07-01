package newsApp.services.scraperService;

import lombok.extern.log4j.Log4j2;
import newsApp.models.newsModel.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class WebScrapperServiceTrendAz {
    private final RestTemplate restTemplate;
    private final String siteAdress = "https://en.trend.az/";

    public WebScrapperServiceTrendAz(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private HttpHeaders pretend_browser() {
        return new HttpHeaders() {{
            add("user-agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        }};
    }

    private String rootPageFromWebAsString(String siteUrl){
        HttpEntity<Object> reqEntity = new HttpEntity<>(pretend_browser());
        return restTemplate.exchange(siteUrl, HttpMethod.GET,reqEntity, String.class).getBody();
    }

    public Collection<News> getAllNews() {
        String fullSite = rootPageFromWebAsString(siteAdress);
        Document doc = Jsoup.parse(fullSite);
        Elements allLinksWithOtherTags = doc.select(".article-link ");
        List<String> allHrefs = allLinksWithOtherTags.stream()
                .filter(e->e.select(".paid-article-icon").size() == 0)
                .map(e -> e.attr("href")).collect(Collectors.toList());
        log.info(allHrefs);
        log.info("size of links: " + allHrefs.size());
        throw new RuntimeException("Implement");
    }
}
