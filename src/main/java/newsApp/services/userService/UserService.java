package newsApp.services.userService;

import lombok.extern.log4j.Log4j2;
import newsApp.exceptions.userException.UserNotFoundException;
import newsApp.models.userModels.NUser;
import newsApp.repo.userRepo.NUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Log4j2
@Service
public class UserService {
    private final PasswordEncoder encoder;
    private final NUserRepository userRepository;

    public UserService(PasswordEncoder encoder, NUserRepository userRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    public NUser findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    public void changeUserPassword(NUser nUser, String newPassword) {
        nUser.setPassword(encoder.encode(newPassword));
        log.info(nUser.toString());
        userRepository.save(nUser); // FIXME problem when saving updates
    }
}
