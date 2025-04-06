package api.giybat.uz.controller;

import api.giybat.uz.dto.post.PostCreateDTO;
import api.giybat.uz.dto.post.PostDTO;
import api.giybat.uz.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<PostDTO> create(@Valid @RequestBody PostCreateDTO dto){
        return ResponseEntity.ok(postService.create(dto));
    }

    @GetMapping("/profile")
    public ResponseEntity<List<PostDTO>> profilePostList(){
        return ResponseEntity.ok(postService.getProfilePostList());
    }
}
