package newsApp.repo.newsRepo;

import newsApp.models.newsModel.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface NewsRepo extends JpaRepository<News,Long> {
    Page<News> findAll(Pageable pageable);

    Page<News> findAllByDomainNotIn(Set<String> notLiked, Pageable pageable);

}
