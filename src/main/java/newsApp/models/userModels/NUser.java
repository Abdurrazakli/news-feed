package newsApp.models.userModels;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class NUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull @NotEmpty
    private String fullname;

    @NotNull @NotEmpty
    @Column(unique = true)
    private String email;

    @NotNull @NotEmpty
    private String password;

    public NUser(@NotNull @NotEmpty String fullname, @NotNull @NotEmpty String email, @NotNull @NotEmpty String password) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
    }
}
