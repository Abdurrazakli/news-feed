package newsApp.models.newsModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"news"})
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
    @ToString.Exclude
    private News news;

    @Column(name = "content",columnDefinition = "TEXT")
    private String content;

    public DetailedNews(News news, String content) {
        this.news=news;
        this.content=content;
    }
}
