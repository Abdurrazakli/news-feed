package newsApp.services.userService;


import lombok.extern.log4j.Log4j2;
import newsApp.models.userModels.NUser;
import newsApp.models.userModels.UserToken;
import newsApp.repo.userRepo.TokenRepo;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class UserTokenizeService {
    private final long EXPIRY_HOURS = 4;

    private final TokenRepo tokenRepo;

    public UserTokenizeService(TokenRepo tokenRepo) {
        this.tokenRepo = tokenRepo;
    }

    public void createUserPasswordResetToken(NUser user, UUID token) {
        UserToken userToken = new UserToken(user, token, LocalDateTime.now().plusHours(EXPIRY_HOURS));
        tokenRepo.save(userToken);
    }

    public String validateToken(UUID token) {
        Optional<UserToken> passToken = tokenRepo.findById(token);
        log.warn("Token in db:" +passToken.toString());
        if (!passToken.isPresent()) return "Token not found";
        return passToken
                .map(userToken -> isTokenExpired(userToken) ? "expired" : null)
                .orElse(null);
    }

    private boolean isTokenExpired(UserToken passToken) {
        return passToken.getExpiryDate().isBefore(LocalDateTime.now());
    }

    public Optional<NUser> getUserByToken(UUID token) {
        return Optional.ofNullable(tokenRepo.findById(token).get().getUser());
    }
}
