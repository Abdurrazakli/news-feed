package newsApp.services.userService;

import newsApp.exceptions.userException.UserNotFoundException;
import newsApp.models.userModels.NUser;
import newsApp.repo.userRepo.NUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final NUserRepository userRepository;

    public UserService(NUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public NUser findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    public void changeUserPassword(NUser nUser, String newPassword) {
        nUser.setPassword(newPassword);
        userRepository.save(nUser);
    }
}
