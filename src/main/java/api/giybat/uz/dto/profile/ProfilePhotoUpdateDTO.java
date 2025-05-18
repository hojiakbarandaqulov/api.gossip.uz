package api.giybat.uz.dto.profile;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProfilePhotoUpdateDTO {
    @NotNull(message = "PhotoId required")
    private String PhotoId;
}
