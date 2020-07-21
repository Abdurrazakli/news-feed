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
            //4
            add(new NewsScraperSkeleton(new Domain("https://www.aljazeera.com/",
                    "Al Jazeera is an independent news organization funded in part by the Qatari government.",
                    "https://www.aljazeera.com/assets/images/aj-logo-lg-124.png"),
                    "https://www.aljazeera.com/news",
                    ".topics-sec-block",
                    ".topics-sec-item",
                    ".topics-sec-item-img a .img-responsive",
                    ".topics-sec-item-img > a",
                    "data-src",
                    ".topics-sec-item-p",
                    ".article-p-wrapper p"
            ));
            //5
            add(new NewsScraperSkeleton(new Domain("https://www.aljazeera.com/",
                    "Al Jazeera is an independent news organization funded in part by the Qatari government.",
                    "https://www.aljazeera.com/assets/images/aj-logo-lg-124.png"),
                    "https://www.aljazeera.com/topics/regions/asia.html",
                    ".topics-sec-block",
                    ".topics-sec-item",
                    ".topics-sec-item-img a .img-responsive",
                    ".topics-sec-item-img > a",
                    "data-src",
                    ".topics-sec-item-p",
                    ".article-p-wrapper p"
            ));
            //6
            add(new NewsScraperSkeleton(new Domain("https://www.businessinsider.co.za/",
                    "Business Insider is a fast-growing business site with deep financial, media, tech, and other industry verticals",
                    "https://cdn.24.co.za/files/Cms/General/d/7010/3500bcae4ed44ba987b7749db0b4b66e.png"),
                    "https://www.businessinsider.co.za/trending/",
                    "#article-slot",
                    "article",
                    ".tf-image img",
                    "div > a",
                    "src",
                    ".tf-title-top",
                    ".articleBody p"
            ));

            //7
            add(new NewsScraperSkeleton(new Domain("https://www.businessinsider.co.za/",
                    "Business Insider is a fast-growing business site with deep financial, media, tech, and other industry verticals",
                    "https://cdn.24.co.za/files/Cms/General/d/7010/3500bcae4ed44ba987b7749db0b4b66e.png"),
                    "https://www.businessinsider.co.za/business/",
                    "#article-slot",
                    "article",
                    ".tf-image img",
                    "div > a",
                    "src",
                    ".tf-title-top",
                    ".articleBody p"
            ));

            //8
            add(new NewsScraperSkeleton(new Domain("https://www.businessinsider.co.za/",
                    "Business Insider is a fast-growing business site with deep financial, media, tech, and other industry verticals",
                    "https://cdn.24.co.za/files/Cms/General/d/7010/3500bcae4ed44ba987b7749db0b4b66e.png"),
                    "https://www.businessinsider.co.za/Money-And-Markets/",
                    "#article-slot",
                    "article",
                    ".tf-image img",
                    "div > a",
                    "src",
                    ".tf-title-top",
                    ".articleBody p"
            ));

            //9
            add(new NewsScraperSkeleton(new Domain("https://www.businessinsider.co.za/",
                    "Business Insider is a fast-growing business site with deep financial, media, tech, and other industry verticals",
                    "https://cdn.24.co.za/files/Cms/General/d/7010/3500bcae4ed44ba987b7749db0b4b66e.png"),
                    "https://www.businessinsider.co.za/Tech/",
                    "#article-slot",
                    "article",
                    ".tf-image img",
                    "div > a",
                    "src",
                    ".tf-title-top",
                    ".articleBody p"
            ));
            //10
            add(new NewsScraperSkeleton(new Domain("https://www.businessinsider.co.za/",
                    "Business Insider is a fast-growing business site with deep financial, media, tech, and other industry verticals",
                    "https://cdn.24.co.za/files/Cms/General/d/7010/3500bcae4ed44ba987b7749db0b4b66e.png"),
                    "https://www.businessinsider.co.za/life/",
                    "#article-slot",
                    "article",
                    ".tf-image img",
                    "div > a",
                    "src",
                    ".tf-title-top",
                    ".articleBody p"
            ));
            //11
            add(new NewsScraperSkeleton(new Domain("https://www.businessinsider.co.za/",
                    "Business Insider is a fast-growing business site with deep financial, media, tech, and other industry verticals",
                    "https://cdn.24.co.za/files/Cms/General/d/7010/3500bcae4ed44ba987b7749db0b4b66e.png"),
                    "https://www.businessinsider.co.za/travel/",
                    "#article-slot",
                    "article",
                    ".tf-image img",
                    "div > a",
                    "src",
                    ".tf-title-top",
                    ".articleBody p"
            ));
            //12
            add(new NewsScraperSkeleton(new Domain("https://www.businessinsider.co.za/",
                    "Business Insider is a fast-growing business site with deep financial, media, tech, and other industry verticals",
                    "https://cdn.24.co.za/files/Cms/General/d/7010/3500bcae4ed44ba987b7749db0b4b66e.png"),
                    "https://www.businessinsider.co.za/executive/",
                    "#article-slot",
                    "article",
                    ".tf-image img",
                    "div > a",
                    "src",
                    ".tf-title-top",
                    ".articleBody p"
            ));
            //13
            add(new NewsScraperSkeleton(new Domain("https://techstartups.com/",
                    "TechStartups.com is an exclusive platform dedicated to covering technology startups.",
                    "https://techstartups.com/wp-content/uploads/2017/12/techstartups.com-logo-v3.png"),
                    "https://techstartups.com/",
                    "body #wrapper .ppb_wrapper .inner_wrapper .post_filter_wrapper",
                    ".post",
                    ".post_img > a > img",
                    ".post_header_title > h5 > a",
                    "src",
                    ".post_header_title > h5 > a",
                    "body .single p"
            ));

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
