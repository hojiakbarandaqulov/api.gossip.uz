package api.giybat.uz.controller;

import api.giybat.uz.dto.ApiResponse;
import api.giybat.uz.dto.post.*;
import api.giybat.uz.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<PostDTO>> create(@Valid @RequestBody PostCreateDTO post) {
        ApiResponse<PostDTO> response = postService.create(post);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<PageImpl<PostDTO>>> postProfile(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                      @RequestParam(value = "size", defaultValue = "10") int size) {
        ApiResponse<PageImpl<PostDTO>> response = postService.postProfile(page - 1, size);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponseDto>> postById(@PathVariable String id) {
        ApiResponse<PostResponseDto> postResponse = postService.postById(id);
        return ResponseEntity.ok(postResponse);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<PostResponseDto>> updatePost(@Valid @RequestBody PostCreateDTO dto,
                                                           @PathVariable String id){
        ApiResponse<PostResponseDto> update = postService.update(id, dto);
        return ResponseEntity.ok(update);
    }
   /* @PostMapping("/filter")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<PostDTO>> filter(@Valid @RequestBody PostFilterDTO filterDTO){
      ApiResponse<List<PostDTO>>response=  postService.filter(filterDTO);
      return ResponseEntity.ok((List<PostDTO>) response);
    }*/

    /*@PostMapping("/filter")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<PostDTO>> filter(@RequestBody PostAdminFilterDTO filterDTO,
                                                @RequestParam(value = "page",defaultValue = "1") Integer page,
                                                @RequestParam(value = "size",defaultValue = "10") Integer size){
        List<PostDTO> postResponse = postService.adminFilter(filterDTO);
        return ResponseEntity.ok(postResponse);
    }*/

}
