package newsApp.models.scraperModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsoup.nodes.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScrapData {
    private Document document;
    private String pathToNews;
}
