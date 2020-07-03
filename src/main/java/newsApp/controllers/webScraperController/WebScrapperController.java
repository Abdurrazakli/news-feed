package newsApp.controllers.webScraperController;

import lombok.extern.log4j.Log4j2;
import newsApp.models.newsModel.News;
import newsApp.services.scraperService.WebScrapperServiceTrendAz;
import newsApp.utils.webScraperUtils.NewsSitesSkeleton;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@Log4j2
@RestController
@RequestMapping("scrap")
public class WebScrapperController {
    private final WebScrapperServiceTrendAz webScrapper;


    public WebScrapperController(WebScrapperServiceTrendAz webScrapper) {
        this.webScrapper = webScrapper;
    }

    @GetMapping("news")
    public Collection<News> handle_news(){

        return webScrapper.getAllNews();
    }
}
