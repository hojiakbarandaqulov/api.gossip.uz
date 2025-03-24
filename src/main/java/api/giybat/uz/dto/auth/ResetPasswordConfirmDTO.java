package api.giybat.uz.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordConfirmDTO {
    @NotBlank(message = "username required")
    private String username;

    @NotBlank(message = "confirm Code required")
    private String confirmCode;

    @NotBlank(message = "newPassword required")
    private String password;
}
