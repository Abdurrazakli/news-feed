package newsApp.repo.userRepo;

import newsApp.models.userModels.NUser;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface NUserRepository extends JpaRepository<NUser,Long> {
    Optional<NUser> findByEmail(String email);
    Boolean existsByEmail(String email);

}
