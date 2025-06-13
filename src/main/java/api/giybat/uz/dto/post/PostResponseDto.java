package api.giybat.uz.dto.post;

import api.giybat.uz.dto.attach.AttachDTO;
import api.giybat.uz.entity.AttachEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponseDto {
    private String id;
    private String title;
    private String content;
    private String photo;
    private LocalDateTime createdDate;
}
