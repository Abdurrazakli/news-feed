package newsApp.utils.webScraperUtils;

import newsApp.models.scraperModel.ScraperSkeleton;

import java.util.ArrayList;
import java.util.List;

public class NewsSitesSkeleton {
    public static List<ScraperSkeleton> get(){
        return new ArrayList<ScraperSkeleton>(){{
            //1
            add(new ScraperSkeleton("https://www.foxnews.com/world","body #wrapper .page .page-content .row .main-content > section",
                    ".content article",".m  a img",".info > header .title > a",".info > header .title > a")
            );
            //2
            add(new ScraperSkeleton()
            );
            //3
            add(new ScraperSkeleton()
            );
            //4
            add(new ScraperSkeleton()
            );
            //5
            add(new ScraperSkeleton()
            );
            //6
            add(new ScraperSkeleton()
            );
        }};
    }
}
