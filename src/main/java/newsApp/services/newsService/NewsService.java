package newsApp.services.newsService;

import lombok.extern.log4j.Log4j2;
import newsApp.exceptions.NewsNotFound;
import newsApp.models.newsModel.Domain;
import newsApp.models.newsModel.News;
import newsApp.models.userModels.NUser;
import newsApp.repo.newsRepo.DomainRepo;
import newsApp.repo.newsRepo.NewsRepo;
import newsApp.repo.userRepo.NUserRepository;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
public class NewsService {
    private final int PAGE_SIZE = 10;
    private final String PARAM_PUBLISHED_DATE = "publishedDate";

    private final NewsRepo newsRepo;
    private final DomainRepo domainRepo;
    private final NUserRepository userRepository;

    public NewsService(NewsRepo newsRepo, DomainRepo domainRepo, NUserRepository userRepository) {
        this.newsRepo = newsRepo;
        this.domainRepo = domainRepo;
        this.userRepository = userRepository;
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

    public Page<News> loadLatestNewsPages_02(int pageNumber, UUID userID) {
        PageRequest pageRequest = PageRequest.of(pageNumber, PAGE_SIZE, Sort.by(PARAM_PUBLISHED_DATE).descending());

        Optional<NUser> loggedInUser = userRepository.findById(userID);

        try {
            return loggedInUser
                    .map(nUser -> nUser.getNotLikedDomains())
                    .map(domainNotLiked -> {
                        if (domainNotLiked.isEmpty()) return newsRepo.findAll(pageRequest);
                        return newsRepo.findAllByDomainNotIn(getDomainNames(domainNotLiked), pageRequest);
                    }).orElseThrow(() -> new NewsNotFound("No News Found"));
        }
        catch (InvalidDataAccessApiUsageException ex){
            log.error(String.format("Loading news error: %s",ex));
            return Page.empty();
        }

    }


    public Page<News> search(String query, String userName) {
        Optional<NUser> loggedInUser = userRepository.findByEmail(userName);
        PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE);

        try {
        return loggedInUser
                .map(NUser::getNotLikedDomains)
                .map(domainNotLiked -> {
                    if (domainNotLiked.isEmpty()) return newsRepo.searchNews02(query, Collections.emptySet(),pageRequest);
                    return newsRepo.searchNews02(query,getDomainNames(domainNotLiked),pageRequest);
                }).orElseThrow(() -> new NewsNotFound("No News Found"));
        }
        catch (InvalidDataAccessApiUsageException ex){
            log.error(String.format("Loading search error: %s",ex));
            return Page.empty();
        }
    }


    private Set<String> getDomainNames(Set<Domain> domains){
        return domains
                .stream()
                .map(Domain::getDomain)
                .collect(Collectors.toSet());
    }
}
