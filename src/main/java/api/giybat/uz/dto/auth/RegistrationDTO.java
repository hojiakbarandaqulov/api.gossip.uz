package api.giybat.uz.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDTO {

    @NotBlank(message = "name required")
    private String name;
    @NotBlank(message = "username required")
    private String username;
    @NotBlank(message = "password required")
    private String password;

}
