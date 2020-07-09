package newsApp.services.userService;


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
        return passToken
                .map(userToken -> isTokenExpired(userToken) ? "expired" : "Token validated")
                .orElse(null);
    }

    private boolean isTokenExpired(UserToken passToken) {
        return passToken.getExpiryDate().isBefore(LocalDateTime.now());
    }

    public Optional<NUser> getUserByToken(UUID token) {
        return Optional.ofNullable(tokenRepo.findById(token).get().getUser());
    }
}
