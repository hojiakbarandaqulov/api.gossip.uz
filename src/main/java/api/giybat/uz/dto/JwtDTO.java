package api.giybat.uz.dto;

import api.giybat.uz.enums.ProfileRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtDTO {
    private Integer id;
    private List<ProfileRole> roleList;
}
