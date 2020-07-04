package newsApp.utils.webScraperUtils;

import newsApp.models.scraperModel.ScraperSkeleton;

import java.util.ArrayList;
import java.util.List;

public class NewsSitesSkeleton {

    public static List<ScraperSkeleton> get(){      // forgot password, login with fb, github;Search;
        return new ArrayList<ScraperSkeleton>(){{
            //1
            add(new ScraperSkeleton("https://www.foxnews.com/world","body #wrapper .page .page-content .row .main-content > section",
                    ".content > article","img",".info > header .title > a","src",".info > header .title > a")
            );
//            //2
//            add(new ScraperSkeleton("https://www.huffpost.com/","body .main #zone-a section",
//                    ".card",".card__image__link .card__image .card__image__src > picture > img",".card__headlines > a","src",".card__headlines > a > h3")
//            );
//            //3
//            add(new ScraperSkeleton("https://www.nytimes.com/section/world","body #app #site-content #collection-world ol",
//                    "li","figure > a > img","figure > a","src","h2 > a")
//            );
//            //4
            add(new ScraperSkeleton("https://www.kyivpost.com/ukraine-politics/exclusive","body .maincontent .wrap section",
                    ".grid-43 .post-excerpt",".img-cont > a > .article-img",".pe-desc .title a","data-img-url",".pe-desc .title a > span")
            );
//            //5
//            add(new ScraperSkeleton()
//            );
//            //6
//            add(new ScraperSkeleton()
//            );
        }};
    }
}
