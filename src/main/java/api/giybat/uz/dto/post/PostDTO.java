package api.giybat.uz.dto.post;

import api.giybat.uz.dto.attach.AttachDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDTO {
    private String id;
    private String title;
    private String content;
    private AttachDTO photo;
    private LocalDateTime createdDate;
    private Boolean visible;
}
