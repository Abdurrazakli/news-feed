package newsApp.repo.newsRepo;

import newsApp.models.newsModel.Domain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DomainRepo extends JpaRepository<Domain,Long> {
    Optional<Domain> findByDomain(String domain);
}
