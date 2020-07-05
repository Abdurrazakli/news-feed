package newsApp.utils.webScraperUtils;

import newsApp.models.scraperModel.NewsScraperSkeleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewsSitesSkeleton {

    public static List<NewsScraperSkeleton> getNewsSkeleton(){      // forgot password, login with fb, github;Search;
        return new ArrayList<NewsScraperSkeleton>(){{
            //1
            add(new NewsScraperSkeleton("https://www.foxnews.com","https://www.foxnews.com/world","body #wrapper .page .page-content .row .main-content > section",
                    ".content > article","img",".info > header .title > a","src",".info > header .title > a")
            );
            //2
            add(new NewsScraperSkeleton("https://www.huffpost.com/","https://www.huffpost.com/","body .main #zone-a section",
                    ".card",".card__image__link .card__image .card__image__src > picture > img",".card__headlines > a","src",".card__headlines > a > h3")
            );
            add(new NewsScraperSkeleton("https://news.az/","https://news.az/","body .wrapper .main--content .post--items > ul","li",
                    ".post--item .post--img > a > img" ,".post--item .post--img > a","data-src",".post--item .post--img ,post--info .title > h2 > a")
            );


            /**
             * Subscription needs for below sites
             */
            //3
//            add(new NewsScraperSkeleton("https://www.nytimes.com/","https://www.nytimes.com/section/world","body #app #site-content #collection-world ol",
//                    "li","figure > a > img","figure > a","src","h2 > a")
//            );
            //4
//            add(new NewsScraperSkeleton("https://www.kyivpost.com","https://www.kyivpost.com/ukraine-politics/exclusive","body .maincontent .wrap section",
//                    ".grid-43 .post-excerpt",".img-cont > a > .article-img",".pe-desc .title a","data-img-url",".pe-desc .title a > span")
//            );
            //5
            //6
//            add(new NewsScraperSkeleton("https://www.washingtonpost.com/","https://www.washingtonpost.com/business/technology/?nid=top_nav_tech","body #pb-root .main-content .story-list",
//                    ".story-list-story",".story-image > a > img",".story-headline > h2 > a","src",".story-headline > h2 > a")
//            );
            //7
//            add(new ScraperSkeleton() for https://en.trend.az/latest/;
//            );
        }};
    }

    public static HashMap<String, String> getNewsParagraphPath(){
        return new HashMap<String, String>(){{
            put("https://www.foxnews.com/world","body .article-body p");
            put("https://www.huffpost.com/","body .entry__text p");
            put("https://news.az/","body .post--content p");
        }};
    }
}
