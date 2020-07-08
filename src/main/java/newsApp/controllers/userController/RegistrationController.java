package newsApp.controllers.userController;

import lombok.extern.log4j.Log4j2;
import newsApp.exceptions.userException.AlreadyExistingUserException;
import newsApp.models.formData.FormRegisterData;
import newsApp.services.userService.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Log4j2
@Controller
@RequestMapping("registration")
public class RegistrationController {
    private final RegistrationService regService;

    public RegistrationController(RegistrationService regService) {
        this.regService = regService;
    }

    /**
    * http://localhost:8080/registration
    * */
    @GetMapping
    public String getRegistrationPage(){
        log.info("Registration clicked");
        return "registration";
    }

  @PostMapping
  public RedirectView createNewUser(FormRegisterData regData) throws AlreadyExistingUserException {
      log.info("------ Create new user sent to service ------");
      regService.createNewUser(regData);
      return new RedirectView("/login");
  }
}
