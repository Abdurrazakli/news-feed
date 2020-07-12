package newsApp.services.newsService;

import lombok.extern.log4j.Log4j2;
import newsApp.exceptions.NewsNotFound;
import newsApp.models.newsModel.News;
import newsApp.repo.newsRepo.NewsRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Log4j2
@Service
public class NewsService {
    private final int PAGE_SIZE = 10;
    private final String PARAM_PUBLISHED_DATE = "publishedDate";

    private final NewsRepo newsRepo;

    public NewsService(NewsRepo newsRepo) {
        this.newsRepo = newsRepo;
    }

    public List<News> search(String query, String name) {
        return null;
    }

    public Page<News> loadLatestNewsPages(int page) {
        Page<News> pages = Page.empty();
        try {
            pages = newsRepo.findAll(PageRequest.of(page, PAGE_SIZE, Sort.by(PARAM_PUBLISHED_DATE).descending()));

            log.info("Loaded page content!");
        }
        catch (Exception ex){
            log.error(String.format("Page not found: %s",ex));
        }
        return pages;
    }

    public News getNewsById(long newsId) throws NewsNotFound {
        return newsRepo.findById(newsId).orElseThrow(()->new NewsNotFound("There is no news for this id"));
    }
}
