package newsApp.models.userModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
