package newsApp.models.scraperModel;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import newsApp.models.newsModel.Domain;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsScraperSkeleton {
    private Domain domain;
    private String address;
    private String pathToSection;
    private String pathToCard;
    private String pathToImg;
    private String pathToNewsLink;
    private String imageAttr;
    private String pathToTitle;
    private String pathToContent;
}
