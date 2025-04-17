package api.giybat.uz.dto;

import api.giybat.uz.enums.GeneralStatus;
import api.giybat.uz.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ProfileDTO {
    private String name;
    private String username;
    private List<ProfileRole> role;
    private String jwt;

}
