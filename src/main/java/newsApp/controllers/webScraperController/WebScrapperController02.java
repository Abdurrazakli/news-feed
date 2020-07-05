package newsApp.controllers.webScraperController;

import lombok.extern.log4j.Log4j2;
import newsApp.models.newsModel.DetailedNews;
import newsApp.services.scraperService.ScraperService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@Log4j2
@RestController
@RequestMapping("v2/scrap")
public class WebScrapperController02 {
    private final ScraperService service;

    public WebScrapperController02(ScraperService service) {
        this.service = service;
    }

    /**
     * http://localhost:8080/v2/scrap/
     */
    @GetMapping
    public Collection<DetailedNews> handle_news(){
        return service.scrap();
    }
}
