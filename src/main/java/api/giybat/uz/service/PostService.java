package api.giybat.uz.service;

import api.giybat.uz.dto.ApiResponse;
import api.giybat.uz.dto.FilterResultDTO;
import api.giybat.uz.dto.post.*;
import api.giybat.uz.entity.PostEntity;
import api.giybat.uz.exps.AppBadException;
import api.giybat.uz.exps.IllegalArgumentException;
import api.giybat.uz.mapper.PostMapper;
import api.giybat.uz.repository.PostRepository;
import api.giybat.uz.repository.customRepository.CustomPostRepository;
import api.giybat.uz.util.SpringSecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.mapper.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
@Slf4j
@Service
public class PostService {
    private final PostRepository postRepository;
    private final CustomPostRepository customPostRepository;
    private final PostMapper mapper;
    private final AttachService attachService;

    public PostService(PostRepository postRepository, CustomPostRepository customPostRepository, PostMapper mapper, AttachService attachService) {
        this.postRepository = postRepository;
        this.customPostRepository = customPostRepository;
        this.mapper = mapper;
        this.attachService = attachService;
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
        postDto.setPhoto(dto.getPhoto());
        return ApiResponse.ok(postDto);
    }

    public ApiResponse<PageImpl<PostDTO>> postProfile(int page, int size) {
        PageRequest pageable= PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<PostEntity> postObj = postRepository.findAll(pageable);
        List<PostDTO> postDTO=new LinkedList<>();
        for (PostEntity postEntity : postObj) {
            PostDTO postDto = mapper.toPostDto(postEntity);
            postDTO.add(postDto);
        }
        Long totalElements = postObj.getTotalElements();
        return new ApiResponse<>(new PageImpl<>(postDTO,pageable,totalElements));
    }

    public ApiResponse<PostResponseDto> update(String id,PostCreateDTO dto){
        PostEntity entity=getPostById(id);
        String deletePhotoId=null;
        if (!dto.getPhoto().getId().equals(entity.getPhotoId())) {
            deletePhotoId=entity.getPhotoId();
        }
        if (!dto.getPhoto().getId().equals(entity.getPhotoId())) {
            attachService.delete(entity.getPhotoId());
        }
        if (deletePhotoId!=null){
            attachService.delete(deletePhotoId);
        }
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setPhotoId(dto.getPhoto().getId());
        postRepository.save(entity);
        return ApiResponse.ok(toPostResponse(entity));
    }
    public ApiResponse<PostResponseDto> postById(String id) {
        PostEntity post=getPostById(id);
        PostResponseDto postResponseDto = new PostResponseDto();
        postResponseDto.setId(post.getId());
        postResponseDto.setTitle(post.getTitle());
        postResponseDto.setContent(post.getContent());
        postResponseDto.setPhoto(post.getPhotoId());
        postResponseDto.setCreatedDate(post.getCreatedDate());
        return ApiResponse.ok(postResponseDto);
    }

   /* public ApiResponse<List<PostDTO>> filter(PostFilterDTO filterDTO, int page, int size) {
        FilterResultDTO<PostEntity> filter = customPostRepository.filter(filterDTO, page, size);

        return null;
    }*/

  /*  public List<PostDTO> adminFilter(PostAdminFilterDTO dto) {

    }*/
    public PostEntity getPostById(String id) {
        return postRepository.findById(id).orElseThrow(()->{
             log.error("post not found",id);
             throw new IllegalArgumentException("Post not found");
         });
    }
    private PostResponseDto toPostResponse(PostEntity postEntity) {
        PostResponseDto dto = mapper.toPostResponnseDto(postEntity);
        dto.setPhoto(postEntity.getPhotoId());
        return dto;
    }
}
