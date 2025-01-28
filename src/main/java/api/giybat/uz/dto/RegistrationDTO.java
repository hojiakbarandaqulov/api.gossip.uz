package api.giybat.uz.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDTO {
    @NotBlank(message = "name required")
    private String name;
    @NotBlank(message = "surname required")
    private String surname;
    @NotBlank(message = "email required")
    private String email;
    @NotBlank(message = "password required")
    private String password;
}
