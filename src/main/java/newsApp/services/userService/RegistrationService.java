package newsApp.services.userService;

import lombok.extern.log4j.Log4j2;
import newsApp.exceptions.userException.UsernameAlreadyExistsEx;
import newsApp.models.formData.FormRegisterData;
import newsApp.models.userModels.NUser;

import newsApp.repo.userRepo.NUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
    private final UserTokenizeService tokenizeService;
    private final String SUBJECT = "Verify your account";

    @Autowired
    private Environment env;
    public RegistrationService(NUserRepository userRepo, PasswordEncoder encoder, EmailSenderService mailService, UserTokenizeService tokenizeService) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.mailService = mailService;
        this.tokenizeService = tokenizeService;
    }

    public Optional<NUser> createNewUser(FormRegisterData regData) {
        if(hasUserRegisteredBefore(regData.getEmail())) throw new UsernameAlreadyExistsEx("This email has already registered");

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
        String CONTEXT_PATH = env.getProperty("my.webdomain");
        return String.format("Please click via this link to verify your account:\r\n %s/user/verify-account?token=%s ", CONTEXT_PATH,token);
    }

    public void registerOAuthUser(NUser nUser) {
        if (!hasUserRegisteredBefore(nUser.getEmail())){
            userRepo.save(nUser);
        }
    }
}
