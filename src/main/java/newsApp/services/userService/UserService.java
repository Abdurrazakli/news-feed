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
    private final PasswordEncoder encoder;


    public UserService(NUserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
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



    public void enableUserProfile(NUser nUser) {
        nUser.setEnabled(true);
        userRepository.save(nUser);
        log.info("User enabled!");
    }


}
