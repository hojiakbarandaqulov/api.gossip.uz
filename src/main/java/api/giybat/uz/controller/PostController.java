package api.giybat.uz.controller;

import api.giybat.uz.dto.ApiResponse;
import api.giybat.uz.dto.post.PostAdminFilterDTO;
import api.giybat.uz.dto.post.PostCreateDTO;
import api.giybat.uz.dto.post.PostDTO;
import api.giybat.uz.dto.post.PostFilterDTO;
import api.giybat.uz.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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
    public ResponseEntity<ApiResponse<PostDTO>> create(@RequestBody PostCreateDTO post){
       ApiResponse<PostDTO> response = postService.create(post);
        return ResponseEntity.ok(response);
    }


   /* @PostMapping("/filter")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<PostDTO>> filter(@Valid @RequestBody PostFilterDTO filterDTO){
      ApiResponse<List<PostDTO>>response=  postService.filter(filterDTO);
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
