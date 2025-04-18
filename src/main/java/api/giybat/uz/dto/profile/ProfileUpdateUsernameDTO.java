package api.giybat.uz.dto.profile;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProfileUpdateUsernameDTO {
    @NotNull(message = "username required")
    private String username;
}
