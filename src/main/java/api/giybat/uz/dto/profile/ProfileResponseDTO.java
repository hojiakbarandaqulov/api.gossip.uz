package api.giybat.uz.dto.profile;


import api.giybat.uz.dto.attach.AttachDTO;
import api.giybat.uz.enums.GeneralStatus;
import api.giybat.uz.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ProfileResponseDTO {
    private String id;
    private String name;
    private String surname;
    private String phone;
    private AttachDTO photo;
    private LocalDateTime createdDate;
    private GeneralStatus status;
    private List<ProfileRole> roles;
}
