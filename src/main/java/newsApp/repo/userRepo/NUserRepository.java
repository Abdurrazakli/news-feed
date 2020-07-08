package newsApp.repo.userRepo;

import newsApp.models.userModels.NUser;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NUserRepository extends JpaRepository<NUser,Long> {
    Optional<NUser> findByEmail(String email);
    Boolean existsByEmail(String email);

}
