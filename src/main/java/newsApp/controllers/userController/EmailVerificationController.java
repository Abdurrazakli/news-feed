package newsApp.controllers.userController;


import lombok.extern.log4j.Log4j2;
import newsApp.models.userModels.NUser;
import newsApp.services.userService.UserService;
import newsApp.services.userService.UserTokenizeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@Controller
@RequestMapping("/user")
public class EmailVerificationController {
    private final UserTokenizeService userTokenizeService;
    private final UserService userService;

    public EmailVerificationController(UserTokenizeService userTokenizeService, UserService userService) {
        this.userTokenizeService = userTokenizeService;
        this.userService = userService;
    }

    /**
     *
     * @param token
     * @return
     */

    @GetMapping("/verify-account")
    public RedirectView verifyAccount(@RequestParam("token") UUID token) {
        log.info("Token of verification: "+token);
        String result = userTokenizeService.validateToken(token);
        Optional<NUser> userByToken = userTokenizeService.getUserByToken(token);
        log.info("Result of verifaction: "+result);
        log.info("User: "+userByToken.toString());
        if (result == null && userByToken.isPresent()){
            log.info("ready to enable account");
            userService.enableUserProfile(userByToken.get());
            return new RedirectView("/login?message=account enabled! You can log in now");
        }
        return new RedirectView("/login?message="+result);
    }
}
