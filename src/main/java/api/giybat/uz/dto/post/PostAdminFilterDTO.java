package api.giybat.uz.dto.post;

import lombok.Data;

import java.util.StringTokenizer;

@Data
public class PostAdminFilterDTO {
    private String profileQuery; //name, surname
    private String postQuery;// id, title
}
