package newsApp.models.newsModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class News {
    @Id
    private UUID id;

    private String title;
    private String news;
    private String source;
    private LocalDateTime publishedDate;
}
