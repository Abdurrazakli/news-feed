package newsApp.models.userModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_tokens")
public class UserToken {
    @Id
    private UUID token;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @OneToOne(targetEntity = NUser.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private NUser user;

    public UserToken(NUser user, UUID token, LocalDateTime expiryDate) {
        this.user=user;
        this.expiryDate=expiryDate;
        this.token=token;
    }
}
