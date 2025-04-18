package api.giybat.uz.dto.confirm;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CodeConfirmDTO {

    @NotBlank(message = "code required")
    private String code;
}
