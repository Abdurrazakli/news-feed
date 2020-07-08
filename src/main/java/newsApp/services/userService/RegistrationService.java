package newsApp.services.userService;

import lombok.extern.log4j.Log4j2;
import newsApp.exceptions.userException.AlreadyExistingUserException;
import newsApp.models.formData.FormRegisterData;
import newsApp.models.userModels.NUser;

import newsApp.repo.userRepo.NUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Log4j2
@Service
public class RegistrationService {
    private final NUserRepository userRepo;
    private final PasswordEncoder encoder;
    public RegistrationService(NUserRepository userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    public Optional<NUser> createNewUser(FormRegisterData regData) throws AlreadyExistingUserException {
        if(hasUserRegisteredBefore(regData.getEmail())) throw new AlreadyExistingUserException("This email has already registered");

        NUser nUser = new NUser(regData.getFullname(), regData.getEmail(), encoder.encode(regData.getPassword()),new String[]{"USER"});
        log.info(String.format("-------New User created: %s-----",nUser));

        return Optional.of(userRepo.save(nUser));
    }

    private boolean hasUserRegisteredBefore(String email){
        return userRepo.existsByEmail(email);
    }
}
