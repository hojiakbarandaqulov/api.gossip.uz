package api.giybat.uz.service;

import api.giybat.uz.dto.attach.AttachDTO;
import api.giybat.uz.dto.post.PostCreateDTO;
import api.giybat.uz.dto.post.PostDTO;
import api.giybat.uz.entity.PostEntity;
import api.giybat.uz.repository.PostRepository;
import api.giybat.uz.util.SpringSecurityUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    private final AttachService attachService;

    public PostService(PostRepository postRepository, AttachService attachService) {
        this.postRepository = postRepository;
        this.attachService = attachService;
    }

    public PostDTO create(PostCreateDTO dto) {
        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(dto.getTitle());
        postEntity.setContent(dto.getContent());
        postEntity.setPhotoId(dto.getPhoto().getId());
        postEntity.setCreatedDate(LocalDateTime.now());
        postEntity.setVisible(true);
        postEntity.setProfileId(SpringSecurityUtil.getCurrentUserId());
        postRepository.save(postEntity);
        return toInfoDTO(postEntity);
    }

    public List<PostDTO> getProfilePostList() {
        Integer id = SpringSecurityUtil.getCurrentUserId();
        List<PostEntity> entityList = postRepository.getAllByProfileIdAndVisibleTrue(id);
        List<PostDTO> dtoList = entityList.stream().map(this::toInfoDTO).toList();
        return dtoList;
    }

    public PostDTO toInfoDTO(PostEntity postEntity) {
        PostDTO dto = new PostDTO();
        dto.setId(postEntity.getId());
        dto.setTitle(postEntity.getTitle());
        dto.setCreatedDate(postEntity.getCreatedDate());
        dto.setPhoto(attachService.attachDTO(postEntity.getPhotoId()));
        return dto;
    }
}

