package api.giybat.uz.controller;

import api.giybat.uz.dto.ApiResponse;
import api.giybat.uz.dto.LoginDTO;
import api.giybat.uz.dto.ProfileDTO;
import api.giybat.uz.dto.RegistrationDTO;
import api.giybat.uz.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registration")
    public ResponseEntity<ApiResponse<String>> registration(@Valid @RequestBody RegistrationDTO dto){
       ApiResponse<String> ok = authService.registration(dto);
        return ResponseEntity.ok(ok);
    }


    @GetMapping("/registration/verification/{token}")
    public ResponseEntity<ApiResponse<String>> registrationVerification(@PathVariable("token") String token){
        ApiResponse<String> ok = authService.regVerification(token);
        return ResponseEntity.ok(ok);
    }

    @GetMapping("/login")
    public ResponseEntity<ApiResponse<ProfileDTO>> login(@Valid @RequestBody LoginDTO dto){
        ApiResponse<ProfileDTO> ok = authService.login(dto);
        return ResponseEntity.ok(ok);
    }
}
