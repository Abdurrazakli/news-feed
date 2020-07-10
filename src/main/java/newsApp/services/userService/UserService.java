package newsApp.services.userService;

import lombok.extern.log4j.Log4j2;
import newsApp.exceptions.NewsNotFound;
import newsApp.exceptions.userException.UserNotFoundException;
import newsApp.models.newsModel.News;
import newsApp.models.userModels.NUser;
import newsApp.repo.newsRepo.NewsRepo;
import newsApp.repo.userRepo.NUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Log4j2
@Service
public class UserService {
    private final NUserRepository userRepository;
    private final NewsRepo newsRepo;
    private final PasswordEncoder encoder;
    private final int PAGE_SIZE = 10;
    private final String PARAM_PUBLISHED_DATE = "publishedDate";

    public UserService(NUserRepository userRepository, NewsRepo newsRepo, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.newsRepo = newsRepo;
        this.encoder = encoder;
    }

    public NUser findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    public void changeUserPassword(NUser nUser, String newPassword) {
        nUser.setPassword(encoder.encode(newPassword));
        userRepository.save(nUser); // Fixme : Not update

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

    public void enableUserProfile(NUser nUser) {
        nUser.setEnabled(true);
        userRepository.save(nUser);
        log.info("User enabled!");
    }

    public News getNewsById(long newsId) throws NewsNotFound {
        return newsRepo.findById(newsId).orElseThrow(()->new NewsNotFound("There is no news for this id"));
    }
}
