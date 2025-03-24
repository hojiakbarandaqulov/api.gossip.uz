package api.giybat.uz.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginDTO {
    @NotBlank(message = "username required")
    private String username;
    @NotBlank(message = "password required")
    private String password;


}
