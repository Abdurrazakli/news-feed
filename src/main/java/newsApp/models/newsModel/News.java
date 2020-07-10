package newsApp.models.newsModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"domainId","detailedNews"})
@Entity
@Table(name = "news", uniqueConstraints=@UniqueConstraint(columnNames = {"news_link"}))
public class News {

    @Column(name = "title",length = 450)
    private String title;

    @Id
    @Column(name = "news_link",length = 450)
    private String newsLink;

    @Column(name = "source", length = 450)
    private String source;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Domain domain;

    @Column(name = "image_path",length = 450)
    private String imagePath;

    @Column(name = "published_date")
    private LocalDateTime publishedDate;

    @JsonIgnore
    @OneToOne(
            mappedBy = "news",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private DetailedNews detailedNews;

    public News(String title, String newsLink, String address,Domain domain, String image,LocalDateTime published) {
        this.title=title;
        this.newsLink = newsLink;
        this.source=address;
        this.domain =domain;
        this.imagePath=image;
        this.publishedDate=published;
    }

    public boolean isNull(){
        return !title.equals("") && !newsLink.equals("") &&
                !source.equals("") && !imagePath.equals("") &&
                publishedDate != null;
    }
}
