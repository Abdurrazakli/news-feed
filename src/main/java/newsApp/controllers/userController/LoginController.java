package newsApp.controllers.userController;

import lombok.extern.log4j.Log4j2;
import newsApp.models.userModels.NUser;
import newsApp.services.userService.UserService;
import newsApp.services.userService.RegistrationService;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;


@Log4j2
@Controller
public class LoginController {
    private static final String authorizationRequestBaseUri = "oauth2/authorize-client";
    Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

    private final RegistrationService registrationService;
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final OAuth2AuthorizedClientService authorizedClientService;

    public LoginController(UserService userService, RegistrationService registrationService, ClientRegistrationRepository clientRegistrationRepository, OAuth2AuthorizedClientService authorizedClientService) {
        this.registrationService = registrationService;
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.authorizedClientService = authorizedClientService;
    }

    @GetMapping("/login")
    public String get_login(Model model){
        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
                .as(Iterable.class);
        if (type != ResolvableType.NONE && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }

        clientRegistrations
                .forEach(registration ->
                        oauth2AuthenticationUrls
                                .put(registration.getClientName(),
                                        authorizationRequestBaseUri + "/" + registration.getRegistrationId()));

        log.info(oauth2AuthenticationUrls.toString());

        model.addAttribute("urls",oauth2AuthenticationUrls);
        return "login";
    }

    @GetMapping("/loginSocial")
    public RedirectView social_login(OAuth2AuthenticationToken authenticationToken, Principal principal){
        log.info("Social login principal: "+ principal.getName());
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(authenticationToken.getAuthorizedClientRegistrationId(), authenticationToken.getName());

        String userInfoEndpointUri = client.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUri();

        if (!StringUtils.isEmpty(userInfoEndpointUri)){
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION,"Bearer "+client.getAccessToken().getTokenValue());

            HttpEntity<String> entity = new HttpEntity<>("", headers);
            ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
            Map userAttributes = response.getBody();
            String name = userAttributes.get("name").toString();
            String email = userAttributes.get("email").toString();
            log.info(email);
            log.info(name);
            NUser nUser = new NUser(name, email, null, true);
            nUser.setRoles(new String[]{"USER"});
            registrationService.registerOAuthUser(nUser);
            log.info("Login process finished for oauth2");
        }

        return new RedirectView("/news");
    }
}
