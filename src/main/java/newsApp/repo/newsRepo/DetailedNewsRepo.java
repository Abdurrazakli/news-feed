package newsApp.repo.newsRepo;

import newsApp.models.newsModel.DetailedNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailedNewsRepo extends JpaRepository<DetailedNews, Long> {

}
