package newsApp.controllers.userController;

import lombok.extern.log4j.Log4j2;
import newsApp.exception.userException.AlreadyExistingUserException;
import newsApp.models.formData.FormRegisterData;
import newsApp.services.userService.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
  public String createNewUser(FormRegisterData regData) throws AlreadyExistingUserException {
      log.info(String.format("------ Create new user sent to service ------"));
      regService.createNewUser(regData);
      return "main-page";
  }
}
