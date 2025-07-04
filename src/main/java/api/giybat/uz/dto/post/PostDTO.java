package api.giybat.uz.dto.post;

import api.giybat.uz.dto.attach.AttachCreateDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDTO {
    private String id;
    private String title;
    private String content;
    @NotNull(message = "photo required")
    private AttachCreateDTO photo;
    private LocalDateTime createdDate;

}
