package api.giybat.uz.service;

import api.giybat.uz.dto.ApiResponse;
import api.giybat.uz.dto.FilterResultDTO;
import api.giybat.uz.dto.post.PostAdminFilterDTO;
import api.giybat.uz.dto.post.PostCreateDTO;
import api.giybat.uz.dto.post.PostDTO;
import api.giybat.uz.dto.post.PostFilterDTO;
import api.giybat.uz.entity.PostEntity;
import api.giybat.uz.mapper.PostMapper;
import api.giybat.uz.repository.PostRepository;
import api.giybat.uz.repository.customRepository.CustomPostRepository;
import api.giybat.uz.util.SpringSecurityUtil;
import org.apache.catalina.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final CustomPostRepository customPostRepository;
    private final PostMapper mapper;

    public PostService(PostRepository postRepository, CustomPostRepository customPostRepository, PostMapper mapper) {
        this.postRepository = postRepository;
        this.customPostRepository = customPostRepository;
        this.mapper = mapper;
    }

    public ApiResponse<PostDTO> create(PostCreateDTO dto) {
        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(dto.getTitle());
        postEntity.setContent(dto.getContent());
        postEntity.setPhotoId(dto.getPhoto().getId());
        postEntity.setVisible(true);
        postEntity.setCreatedDate(LocalDateTime.now());
        postEntity.setProfileId(SpringSecurityUtil.getProfileId());
        postRepository.save(postEntity);
        PostDTO postDto = mapper.toPostDto(postEntity);
        return ApiResponse.ok(postDto);
    }

    public ApiResponse<List<PostDTO>> filter(PostFilterDTO filterDTO, int page, int size) {
        FilterResultDTO<PostEntity> filter = customPostRepository.filter(filterDTO, page, size);

    }

    /*public List<PostDTO> adminFilter(PostAdminFilterDTO dto) {

    }*/
}
