package newsApp.models.newsModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class News {
    private String title;
    private String news;
    private String source;
    private LocalDateTime publishedDate;
}
