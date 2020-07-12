package newsApp.utils.webScraperUtils;

import newsApp.models.newsModel.Domain;
import newsApp.models.scraperModel.NewsScraperSkeleton;

import java.util.ArrayList;
import java.util.List;

public class NewsSitesStructures {

    public static List<NewsScraperSkeleton> getNewsSkeleton(){      // forgot password, login with fb, github;Search;
        return new ArrayList<NewsScraperSkeleton>(){{
            //1
            add(new NewsScraperSkeleton(
                            new Domain("https://www.foxnews.com",
                                       "Get the latest breaking and in-depth U.S. news headlines, photos and videos on FoxNews.com.",
                                       "https://upload.wikimedia.org/wikipedia/commons/d/d4/Fox_News_Channel_logo.png"),
                    "https://www.foxnews.com/world",
                    "body #wrapper .page .page-content .row .main-content > section",
                    ".content > article",
                    "img",
                    ".info > header .title > a",
                    "src",
                    ".info > header .title > a",
                    "body .article-body p")
            );
            //2
            add(new NewsScraperSkeleton(
                            new Domain("https://www.huffpost.com/",
                    "HuffPost is for the people — not the powerful. We are empathetic reporters and observers. We hold power accountable. We entertain without guilt.",
                    "https://www.prisa.com/uploads/2017/04/resized/850_850huffpost-new.jpg"),
                    "https://www.huffpost.com/",
                    "body .main #zone-a section",
                    ".card",
                    ".card__image__link .card__image .card__image__src > picture > img",
                    ".card__headlines > a",
                    "src",
                    ".card__headlines > a > h3",
                    "body .entry__text p")
            );
            //3
            add(new NewsScraperSkeleton(
                            new Domain("https://news.az/",
                            "News.Az - Latest news from Azerbaijan.",
                            "https://news.az/app/img/logo.png"),
                    "https://news.az/",
                    "body .wrapper .main--content .post--items > ul",
                    "li",
                    ".post--item .post--img > a > img" ,
                    ".post--item .post--img > a",
                    "data-src",
                    ".post--item .post--img ,post--info .title > h2 > a",
                    "body .post--content p")
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
}
