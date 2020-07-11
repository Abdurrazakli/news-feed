package newsApp.models.newsModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import newsApp.models.userModels.NUser;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"news","users"})
@Table(name = "domains")
public class Domain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true,name = "domain")
    private String domain;

    @Column(name = "domain_info",length = 300)
    private String domainInfo;

    @Column(name = "domain_logo_link",length = 450)
    private String domainLogoLink;

    @OneToMany(mappedBy = "domain")
    @JsonIgnore
    @ToString.Exclude
    private Set<News> news;

    @JsonIgnore
    @ManyToMany
    @ToString.Exclude
    private Set<NUser> users;

    public Domain(String domain, String domainInfo, String domainLogoLink) {
        this.domain = domain;
        this.domainInfo = domainInfo;
        this.domainLogoLink = domainLogoLink;
    }
}