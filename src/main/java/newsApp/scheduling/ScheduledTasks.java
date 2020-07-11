package newsApp.scheduling;

import lombok.extern.log4j.Log4j2;
import newsApp.services.scraperService.ScraperService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ScheduledTasks {
    private final static int SCHEDULE_INTERVAL_HOURS = 2;
    private final static long SCHEDULE_INTERVAL_MILLIS = SCHEDULE_INTERVAL_HOURS * 60 * 60 * 1000;
    private final static long INITIAL_DELAY_MILLIS = 20 * 1000;
    private final ScraperService scrapper;

    public ScheduledTasks(ScraperService scrapper) {
        this.scrapper = scrapper;
    }

    @Scheduled(fixedRate = SCHEDULE_INTERVAL_MILLIS,initialDelay = INITIAL_DELAY_MILLIS)
    public void scheduleNewsUpdate(){
        log.info("News update called");
        try {
            scrapper.scrap();
        }
        catch (Exception ex){
            log.error(String.format("Scrapping error: %s",ex));
        }
    }
}
