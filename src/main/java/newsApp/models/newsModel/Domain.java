package newsApp.models.newsModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import newsApp.models.userModels.NUser;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "domains")
public class Domain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "domain")
    private String domain;

    @OneToMany(mappedBy = "domainId")
    @JsonIgnore
    private Set<News> news;

    @JsonIgnore
    @ManyToMany
    private Set<NUser> users;

    public Domain(String domain) {
        this.domain = domain;
    }
}