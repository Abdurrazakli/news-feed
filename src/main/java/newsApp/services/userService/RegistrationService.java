package newsApp.services.userService;

import newsApp.repo.userRepo.NUserRepository;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private final NUserRepository userRepo;

    public RegistrationService(NUserRepository userRepo) {
        this.userRepo = userRepo;
    }
}
