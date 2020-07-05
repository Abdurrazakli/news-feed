package newsApp.models.userModels;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class NUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull @NotEmpty
    private String fullname;

    @NotNull @NotEmpty
    private String email;

    @NotNull @NotEmpty
    private String password;

    public NUser(@NotNull @NotEmpty String fullname, @NotNull @NotEmpty String email, @NotNull @NotEmpty String password) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
    }
}
