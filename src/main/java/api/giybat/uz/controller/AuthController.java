package api.giybat.uz.controller;

import api.giybat.uz.dto.RegistrationDTO;
import api.giybat.uz.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody RegistrationDTO dto){
        return ResponseEntity.ok(authService.registration(dto));
    }
}
