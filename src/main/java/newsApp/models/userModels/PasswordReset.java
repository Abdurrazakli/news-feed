package newsApp.models.userModels;

import lombok.Data;

import java.util.UUID;

@Data
public class PasswordReset {
    private String oldPassword;
    private UUID token;

    private String newPassword;

}
