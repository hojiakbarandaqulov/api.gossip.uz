package api.giybat.uz.dto.attach;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AttachResponseDTO {
    @NotNull(message = "photoId required")
    private String id;

    private String url;
}
