package newsApp.services.userService;

import lombok.extern.log4j.Log4j2;
import newsApp.exceptions.DomainNotExists;
import newsApp.exceptions.userException.UserNotFoundException;
import newsApp.models.newsModel.Domain;
import newsApp.models.userModels.NUser;
import newsApp.repo.newsRepo.DomainRepo;
import newsApp.repo.userRepo.NUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Service
public class UserService {
    private final NUserRepository userRepository;
    private final PasswordEncoder encoder;
    private final DomainRepo domainRepo;

    public UserService(NUserRepository userRepository, PasswordEncoder encoder, DomainRepo domainRepo) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.domainRepo = domainRepo;
    }

    public NUser findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    public void changeUserPassword(NUser nUser, String newPassword) {
        nUser.setPassword(encoder.encode(newPassword));
        userRepository.save(nUser); // Fixme : Not update
    }



    public void enableUserProfile(NUser nUser) {
        nUser.setEnabled(true);
        userRepository.save(nUser);
        log.info("User enabled!");
    }


    public void dislikeDomain(UUID id, long domainId) {
        NUser user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        Domain unlikedDomain = domainRepo.findById(domainId).orElseThrow(DomainNotExists::new);
        Set<Domain> notLikedDomainsByUser = user.getNotLikedDomains();

        notLikedDomainsByUser.add(unlikedDomain);
        user.setNotLikedDomains(notLikedDomainsByUser);
        log.info("Domain disliked by user!");
        userRepository.save(user);
    }

    public List<Domain> getDomainsOfUser(UUID id) {
        NUser user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        Set<String> notLikedDomains = user.getNotLikedDomains().stream().map(d -> d.getDomain()).collect(Collectors.toSet());

        if(notLikedDomains.size()==0) return domainRepo.findAll();
        else return domainRepo.findAllByDomainNotIn(notLikedDomains);
    }
}
