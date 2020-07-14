package newsApp.controllers.userController;


import lombok.extern.log4j.Log4j2;
import newsApp.models.userModels.NUser;
import newsApp.models.userModels.PasswordReset;
import newsApp.repo.userRepo.NUserRepository;
import newsApp.services.userService.EmailSenderService;
import newsApp.services.userService.UserService;
import newsApp.services.userService.UserTokenizeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Controller
@RequestMapping("/user")
public class PasswordResetController {

    private final EmailSenderService senderService;
    private final UserTokenizeService userTokenizeService;
    private final UserService userService;
    private final String contextPath = "http://localhost:8080"; // TODO In Production will not work cget host from heroku env;
    private final String subject = "Reset password";

    public PasswordResetController(EmailSenderService senderService, NUserRepository repository,
                                   UserTokenizeService userTokenizeService, UserService userService) {
        this.senderService = senderService;
        this.userTokenizeService = userTokenizeService;
        this.userService = userService;
    }

    /**
     * http://localhost:8080/user/reset-password
     * @return
     */

    @GetMapping("/reset-password")
    public String password_reset(){
        log.info("Reset_GET_request");
        return "forgot-password";
    }

    @PostMapping("/reset-password")
    public  String resetPassword(HttpServletRequest request,
                                 @RequestParam("email") String email){
        UUID token = UUID.randomUUID();
        log.info("Reset_GET_request");
        NUser nUser = userService.findByEmail(email);

        userTokenizeService.createUserToken(nUser, token);


        senderService.sendEmail(subject,constructMessageBody(token.toString()),nUser);
        return "reset-send";
    }

    private String constructMessageBody(String token){
        return "Reset your password via this link:\r\n" + contextPath + "/user/change-password?token=" + token + "\n\nNote: link will be valid for 4 hours";
    }


    @GetMapping("/change-password")
    public String changePasswordPage(Model model, @RequestParam("token") UUID token){
        log.info("Token: "+ token.toString());
        String result = userTokenizeService.validateToken(token);
        log.info("Result of validation: "+ result);
        if (result != null){
            return "redirect:/login?password_not_reset=false&message="+result;
        }else {
            model.addAttribute("token",token.toString());
            return "change-password";
        }
    }

    @PostMapping("/save-password")
    public RedirectView saveNewPassword(PasswordReset passwordReset){
        log.info("Save changes view activated!");
        log.info(passwordReset.toString());
        String result = userTokenizeService.validateToken(passwordReset.getToken());
        if (result != null ) return new RedirectView("/login?password_not_reset=false");
        Optional<NUser> userByToken = userTokenizeService.getUserByToken(passwordReset.getToken());
        if(userByToken.isPresent()) {
            userService.changeUserPassword(userByToken.get(),passwordReset.getNewPassword());
            return new RedirectView("/login?password_reset=true");
        }else {
            return new RedirectView("/login?password_not_reset=false");
        }
    }


}
