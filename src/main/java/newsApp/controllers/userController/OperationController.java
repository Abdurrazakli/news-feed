package newsApp.controllers.userController;

import lombok.extern.log4j.Log4j2;
import newsApp.models.newsModel.Domain;
import newsApp.models.userModels.NUserDetails;
import newsApp.services.userService.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
@Log4j2
@Controller
public class OperationController {
    private static final String EMAIL = "email";
    private static final String NAME = "name";
    private final UserService userService;

    public OperationController(UserService userService) {
        this.userService = userService;
    }

    /**
     * http://localhost:8080/disable-news
     *@return
     */
    @GetMapping("/disable-news")
    public String getDisableNewsPage(Authentication auth, Model model){
        String email = null;
        String name;
        if ((auth instanceof OAuth2AuthenticationToken)) {
            email = userService.getSpecificDataFromOauth2((OAuth2AuthenticationToken) auth, EMAIL);
            name = userService.getSpecificDataFromOauth2((OAuth2AuthenticationToken) auth, NAME);
        } else {
            email = auth.getName();
            NUserDetails userDetails = (NUserDetails) auth.getPrincipal();
            name = userDetails.getFullName();
        }

        log.info("Main-page GET request worked!");

        log.info("User email " + email);
        log.info("User name " + name);
        List<Domain> domains = userService
                .getActiveDomainsOfUser(email);
        log.info("User's domain list returned!");
        model.addAttribute("allDomains",domains);
        model.addAttribute("username",name);

        return "disable-news";
    }
    /**
     * http://localhost:8080/disable-news/{id}
     *@return
     */
    @PostMapping("/disable-news/{domainId}")
    public RedirectView getDisableNewsPage(@PathVariable("domainId") long domainId, Authentication auth){
        String email = (auth instanceof OAuth2AuthenticationToken) ? userService.getSpecificDataFromOauth2((OAuth2AuthenticationToken) auth,EMAIL) : auth.getName();

        userService.disableDomain(email,domainId);
        return new RedirectView("/disable-news");
    }

    /**
     * http://localhost:8080/disable-news
     *@return
     */
    @GetMapping("/enable-news")
    public String getEnableNewsPage(Authentication auth, Model model){
        String email = null;
        String name;
        if ((auth instanceof OAuth2AuthenticationToken)) {
            email = userService.getSpecificDataFromOauth2((OAuth2AuthenticationToken) auth, EMAIL);
            name = userService.getSpecificDataFromOauth2((OAuth2AuthenticationToken) auth, NAME);
        } else {
            email = auth.getName();
            NUserDetails userDetails = (NUserDetails) auth.getPrincipal();
            name = userDetails.getFullName();
        }

        log.info("Main-page GET request worked!");

        log.info("User email " + email);
        log.info("User name " + name);

        List<Domain> domains = userService.getDisabledDomainsOfUser(email);
        log.info("User's disabled list returned!"+domains);
        model.addAttribute("allDomains",domains);
        model.addAttribute("username",name);

        return "enable-news";
    }
    /**
     * http://localhost:8080/disable-news/{id}
     *@return
     */
    @PostMapping("/enable-news/{domainId}")
    public RedirectView getEnableNewsPage(@PathVariable("domainId") long domainId, Authentication auth){
        String email = (auth instanceof OAuth2AuthenticationToken) ? userService.getSpecificDataFromOauth2((OAuth2AuthenticationToken) auth,EMAIL) : auth.getName();

        userService.enableDomain(email,domainId);
        return new RedirectView("/enable-news");
    }
}
