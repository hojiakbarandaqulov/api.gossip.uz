package api.giybat.uz.dto.profile;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProfileUpdatePasswordDTO {
    @NotNull(message = "currentPassword required")
    private String currentPassword;

    @NotNull(message = "newPassword required")
    private String newPassword;
}
