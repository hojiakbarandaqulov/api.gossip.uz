package api.giybat.uz.dto.attach;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachDTO {
    private String id;
    private String originalName;
    private String path;
    private Long size;
    private String extension;
    private LocalDateTime createdDate;
    private String url;

    public AttachDTO(String id, String s) {
        this.id = id;
    }

    public AttachDTO() {
    }
}
