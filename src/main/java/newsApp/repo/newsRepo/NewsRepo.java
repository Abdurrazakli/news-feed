package newsApp.repo.newsRepo;

import newsApp.models.newsModel.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface NewsRepo extends JpaRepository<News,Long> {
    Page<News> findAll(Pageable pageable);

    Page<News> findAllByDomainNotIn(Set<Long> notLiked, Pageable pageable);


    @Query(value = " SELECT news from Domain domain\n" +
            "           left outer join domain.news as news left outer join\n" +
            "            news.detailedNews as detailedNews  where (domain.domain not in (?2)) AND " +
            "((countOfString(?1,detailedNews.content) + countOfString(?1,news.title)) > 0)\n" +
            "           ORDER BY (countOfString(?1,detailedNews.content) + countOfString(?1,news.title)) DESC")
    Page<News> searchNews02(String query, Set<String> notLikedDomains, Pageable pageable);

}
