package newsApp.repo.newsRepo;

import newsApp.models.newsModel.Domain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DomainRepo extends JpaRepository<Domain,Long> {
    Optional<Domain> findByDomain(String domain);
    List<Domain> findAllByDomainNotIn(Set<String> notLiked);
    List<Domain> findAllByDomainIn(Set<String> disabled);

}
