package newsApp.models.scraperModel;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScraperSkeleton {
    private String webSite;
    private String pathToSection;
    private String pathToContent;
    private String pathToImg;
    private String pathToNewsLink;
    private String pathToTitle;
}
