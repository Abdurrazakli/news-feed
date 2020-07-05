package newsApp.models.scraperModel;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsScraperSkeleton {
    private String domain;
    private String address;
    private String pathToSection;
    private String pathToContent;
    private String pathToImg;
    private String pathToNewsLink;
    private String imageAttr;
    private String pathToTitle;
}
