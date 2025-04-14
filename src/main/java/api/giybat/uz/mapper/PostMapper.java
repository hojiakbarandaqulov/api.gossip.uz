package api.giybat.uz.mapper;

import api.giybat.uz.dto.post.PostDTO;
import api.giybat.uz.entity.PostEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostDTO toPostDto(PostEntity entity);
}
