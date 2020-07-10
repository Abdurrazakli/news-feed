package newsApp.repo.userRepo;

import newsApp.models.userModels.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TokenRepo extends JpaRepository<UserToken, UUID> {

}
