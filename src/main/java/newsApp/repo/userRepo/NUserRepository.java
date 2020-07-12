package newsApp.repo.userRepo;

import newsApp.models.userModels.NUser;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Transactional
public interface NUserRepository extends JpaRepository<NUser,UUID> {
    Optional<NUser> findByEmail(String email);
    Boolean existsByEmail(String email);
}
