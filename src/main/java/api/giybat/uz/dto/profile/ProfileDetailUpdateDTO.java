package api.giybat.uz.dto.profile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProfileDetailUpdateDTO {
    @NotBlank(message = "name required")
    private String name;

}
