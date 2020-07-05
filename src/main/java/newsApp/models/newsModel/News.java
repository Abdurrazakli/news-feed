package newsApp.models.newsModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;


import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title",length = 450)
    private String title;

    @Column(name = "news_link",length = 450)
    private String newsLink;

    @Column(name = "source", length = 450)
    private String source;

    @Column(name = "image_path",length = 450)
    private String imagePath;

    @Column(name = "publiched_date")
    private LocalDateTime publishedDate;

    @OneToOne(
            mappedBy = "news",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private DetailedNews detailedNews;

    public News(String title, String newsLink, String address, String image,LocalDateTime published) {
        this.title=title;
        this.newsLink = newsLink;
        this.source=address;
        this.imagePath=image;
        this.publishedDate=published;
    }

    public boolean isNull(){
        return !title.equals("") && !newsLink.equals("") &&
                !source.equals("") && !imagePath.equals("") &&
                publishedDate != null;
    }
}
