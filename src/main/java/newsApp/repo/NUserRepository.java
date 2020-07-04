package newsApp.repo;

import newsApp.models.userModels.NUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NUserRepository extends JpaRepository<NUser,Long> {
}
