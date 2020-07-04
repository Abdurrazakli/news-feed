package newsApp.controllers.userController;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("registration")
public class RegistrationController {
   /**
    * http://localhost:8080/registration
    * */
    @GetMapping
    public String getRegistrationPage(){
        log.info("Registration clicked");
        return "registration";
    }
}
