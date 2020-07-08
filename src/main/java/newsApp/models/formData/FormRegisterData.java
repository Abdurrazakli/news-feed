package newsApp.models.formData;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FormRegisterData {
    private String fullname;
    private String email;
    private String password;
}
