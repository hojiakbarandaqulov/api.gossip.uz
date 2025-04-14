package api.giybat.uz.service;

import api.giybat.uz.dto.post.PostCreateDTO;
import api.giybat.uz.dto.post.PostDTO;
import api.giybat.uz.entity.PostEntity;
import api.giybat.uz.mapper.PostMapper;
import api.giybat.uz.repository.PostRepository;
import api.giybat.uz.util.SpringSecurityUtil;
import org.apache.catalina.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper mapper;

    public PostService(PostRepository postRepository, PostMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    public PostDTO create(PostCreateDTO dto) {
        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(dto.getTitle());
        postEntity.setContent(dto.getContent());
        postEntity.setPhotoId(dto.getPhoto().getId());
        postEntity.setVisible(true);
        postEntity.setCreatedDate(LocalDateTime.now());
        postEntity.setProfileId(SpringSecurityUtil.getCurrentUserId());
        postRepository.save(postEntity);
        PostDTO postDto = mapper.toPostDto(postEntity);
        return postDto;
    }


}
