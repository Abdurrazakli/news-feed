package newsApp.models.userModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import newsApp.models.newsModel.Domain;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class NUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String fullName;

    @Column(unique = true)
    private String email;

    private String password;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "unlikedDomains",
                joinColumns = {@JoinColumn(
                        name = "user_id",
                        referencedColumnName = "id")},
                inverseJoinColumns = {@JoinColumn(
                        name = "domain_id",
                        referencedColumnName = "id"
                )}
    )
                                             // one user can have multiple domains;           \\   \
                                                //                                            >=========>>> ManyToMAny
    private Set<Domain> NotLikedDomains;     // one domain can be disliked by multiple users; //   /

    private String roles;

    @Transient
    private final static String DELIMITER = ":";

    public String[] getRoles() {
        return roles == null || roles.isEmpty() ? new String[]{}
                : roles.split(DELIMITER);
    }

    public void setRoles(String[] roles) {
        this.roles = String.join(DELIMITER, roles);
    }


    public NUser(String fullname, String email, String password,String[] roles ) {
        this.fullName = fullname;
        this.email = email;
        this.password = password;
    }
}
