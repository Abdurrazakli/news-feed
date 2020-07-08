package newsApp.models.userModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import newsApp.models.newsModel.Domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @NotEmpty
    private String fullName;

    @NotNull
    @NotEmpty
    @Column(unique = true)
    private String email;

    @NotNull
    @NotEmpty
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


    public NUser(@NotNull @NotEmpty String fullname, @NotNull @NotEmpty String email, @NotNull @NotEmpty String password,String[] roles ) {
        this.fullName = fullname;
        this.email = email;
        this.password = password;
    }
}
