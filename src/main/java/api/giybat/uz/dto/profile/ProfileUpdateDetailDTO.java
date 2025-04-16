package api.giybat.uz.dto.profile;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProfileUpdateDetailDTO {
    @NotNull(message = "name required")
    private String name;
}
