package api.giybat.uz.dto.post;

import api.giybat.uz.dto.attach.AttachCreateDTO;
import api.giybat.uz.dto.attach.AttachDTO;
import api.giybat.uz.entity.AttachEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class PostCreateDTO {
    @NotBlank(message = "Title required")
    @Length(min=5,max=255,message = "min-5 , max-255")
    private String title;

    @NotBlank(message = "Content required")
    private String content;

    @NotBlank(message = "Photo required")
    private AttachDTO photo;
}
