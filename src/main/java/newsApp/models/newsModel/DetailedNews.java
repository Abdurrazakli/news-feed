package newsApp.models.newsModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "detailed_news")
public class DetailedNews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonIgnore
    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private News news;

    @Column(name = "content",columnDefinition = "TEXT")
    private String content;

    public DetailedNews(News news, String content) {
        this.news=news;
        this.content=content;
    }
}
