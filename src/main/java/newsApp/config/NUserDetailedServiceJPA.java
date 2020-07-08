package newsApp.config;

import lombok.extern.log4j.Log4j2;
import newsApp.models.userModels.NUser;
import newsApp.models.userModels.NUserDetails;
import newsApp.repo.userRepo.NUserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Log4j2
@Configuration
public class NUserDetailedServiceJPA implements UserDetailsService {
    private final NUserRepository repo;

    public NUserDetailedServiceJPA(NUserRepository repo)  {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       log.info("try to login with username: "+email);
        return repo.findByEmail(email)
                .map(this::mapper_to_userDetail)
                .orElseThrow(()->new UsernameNotFoundException(
                        String.format("User %s not found in db",email)
                ));
    }

    private UserDetails mapper_to_userDetail(NUser nUser){
        return new NUserDetails(
                nUser.getId(),
                nUser.getEmail(),
                nUser.getFullName(),
                nUser.getPassword(),
                nUser.getRoles()
        );
    }
}
