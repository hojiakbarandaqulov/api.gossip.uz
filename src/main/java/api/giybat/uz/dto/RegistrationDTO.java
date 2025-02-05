package api.giybat.uz.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
public class RegistrationDTO {
    @NotBlank(message = "name required")
    private String name;
    @NotBlank(message = "surname required")
    private String surname;
    @NotBlank(message = "username required")
    private String username;
    @NotBlank(message = "password required")
    private String password;

}
