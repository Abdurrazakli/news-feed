package newsApp.services.userService;

import lombok.extern.log4j.Log4j2;
import newsApp.exceptions.userException.AlreadyExistingUserException;
import newsApp.models.formData.FormRegisterData;
import newsApp.models.userModels.NUser;

import newsApp.repo.userRepo.NUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class RegistrationService {
    private final NUserRepository userRepo;
    private final PasswordEncoder encoder;
    private final EmailSenderService mailService;
    private final String CONTEXT_PATH = "http:/localhost:8080";
    private final UserTokenizeService tokenizeService;
    private final String SUBJECT = "Verify your account";

    public RegistrationService(NUserRepository userRepo, PasswordEncoder encoder, EmailSenderService mailService, UserTokenizeService tokenizeService) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.mailService = mailService;
        this.tokenizeService = tokenizeService;
    }

    public Optional<NUser> createNewUser(FormRegisterData regData) {
        if(hasUserRegisteredBefore(regData.getEmail())) throw new AlreadyExistingUserException("This email has already registered");

        NUser nUser = new NUser(regData.getFullname(), regData.getEmail(), encoder.encode(regData.getPassword()),false);
        nUser.setRoles(new String[]{"USER"});
        NUser saveUser = userRepo.save(nUser);
        sendVerificationEmail(nUser);

        log.info(String.format("-------New User created: %s-----",nUser));

        return Optional.of(saveUser);
    }

    private boolean hasUserRegisteredBefore(String email){
        return userRepo.existsByEmail(email);
    }

    private void sendVerificationEmail(NUser nUser){
        UUID token = UUID.randomUUID();
        tokenizeService.createUserToken(nUser,token);
        mailService.sendEmail(SUBJECT,constructBody(token.toString()),nUser);
    }

    private String constructBody(String token){
        return String.format("Please click <a href = \"%s/user/verify-account?token=%s\">here</a> to verify your account!", CONTEXT_PATH,token);
    }
}
