package newsApp.models.newsModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "domains")
public class Domain {

    @Id
    @Column(name = "domain")
    private String domain;

    @OneToMany(mappedBy = "domainId")
    @JsonIgnore
    private Set<News> news;

    public Domain(String domain) {
        this.domain = domain;
    }

}
