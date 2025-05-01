package api.giybat.uz.dto.post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostFilterDTO {
    private String query;
    private String exceptId;
}
