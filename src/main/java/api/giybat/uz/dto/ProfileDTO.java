package api.giybat.uz.dto;

import api.giybat.uz.enums.GeneralStatus;
import api.giybat.uz.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProfileDTO {

//    private String id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private ProfileRole role;
    private GeneralStatus status;
    private LocalDateTime createdDate=LocalDateTime.now();
    private Boolean visible=Boolean.TRUE;
}
