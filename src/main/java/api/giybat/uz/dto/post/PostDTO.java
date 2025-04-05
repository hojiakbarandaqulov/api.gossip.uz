package api.giybat.uz.dto.post;

import api.giybat.uz.dto.attach.AttachCreateDTO;
import api.giybat.uz.dto.attach.AttachDTO;
import api.giybat.uz.entity.AttachEntity;
import api.giybat.uz.entity.ProfileEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDTO {
    @NotBlank(message = "id required")
    private String id;
    private String title;
    private String content;;
    private AttachDTO photo;
    private LocalDateTime createdDate;
    private Boolean visible=true;
}
