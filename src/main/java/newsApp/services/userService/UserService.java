package newsApp.services.userService;

import lombok.extern.log4j.Log4j2;
import newsApp.exceptions.DomainNotExists;
import newsApp.exceptions.userException.UserNotFoundException;
import newsApp.models.newsModel.Domain;
import newsApp.models.userModels.NUser;
import newsApp.repo.newsRepo.DomainRepo;
import newsApp.repo.userRepo.NUserRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
public class UserService {
    private final NUserRepository userRepository;
    private final PasswordEncoder encoder;
    private final DomainRepo domainRepo;
    private final OAuth2AuthorizedClientService authorizedClientService;


    public UserService(NUserRepository userRepository, PasswordEncoder encoder, DomainRepo domainRepo, OAuth2AuthorizedClientService authorizedClientService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.domainRepo = domainRepo;
        this.authorizedClientService = authorizedClientService;
    }

    public NUser findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    public void changeUserPassword(NUser nUser, String newPassword) {
        nUser.setPassword(encoder.encode(newPassword));
        userRepository.save(nUser);
    }



    public void enableUserProfile(NUser nUser) {
        nUser.setEnabled(true);
        userRepository.save(nUser);
        log.info("User enabled!");
    }


    public void disableDomain(String email, long domainId) {
        NUser user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        Domain unlikedDomain = domainRepo.findById(domainId).orElseThrow(DomainNotExists::new);
        Set<Domain> notLikedDomainsByUser = user.getNotLikedDomains();

        notLikedDomainsByUser.add(unlikedDomain);
        user.setNotLikedDomains(notLikedDomainsByUser);
        log.info("Domain disliked by user!");
        userRepository.save(user);
    }

    public List<Domain> getActiveDomainsOfUser(String email) {
        NUser user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        Set<String> notLikedDomains = user.getNotLikedDomains().stream().map(d -> d.getDomain()).collect(Collectors.toSet());

        if(notLikedDomains.size()==0) return domainRepo.findAll();
        else return domainRepo.findAllByDomainNotIn(notLikedDomains);
    }

    public List<Domain> getDisabledDomainsOfUser(String email) {
        NUser user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        Set<String> disabledDomains = user.getNotLikedDomains()
                .stream()
                .map(Domain::getDomain)
                .collect(Collectors.toSet());

        return domainRepo.findAllByDomainIn(disabledDomains);
    }

    public void enableDomain(String email, long domainId) {
        NUser user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        Domain unlikedDomain = domainRepo.findById(domainId).orElseThrow(DomainNotExists::new);
        Set<Domain> notLikedDomainsByUser = user.getNotLikedDomains();

        notLikedDomainsByUser.remove(unlikedDomain);
        user.setNotLikedDomains(notLikedDomainsByUser);
        log.info("Domain enabled by user!");
        userRepository.save(user);
    }


    public String getSpecificDataFromOauth2(OAuth2AuthenticationToken auth, String requiredData) {
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(auth.getAuthorizedClientRegistrationId(), auth.getName());

        String userInfoEndpointUri = client.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUri();

        if (!StringUtils.isEmpty(userInfoEndpointUri)) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken().getTokenValue());

            HttpEntity<String> entity = new HttpEntity<>("", headers);
            ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
            Map userAttributes = response.getBody();
            return userAttributes.get(requiredData).toString();
        } else throw new UserNotFoundException();

    }
}
