package api.giybat.uz.controller;

import api.giybat.uz.dto.ApiResponse;
import api.giybat.uz.dto.confirm.CodeConfirmDTO;
import api.giybat.uz.dto.post.PostDTO;
import api.giybat.uz.dto.profile.ProfileAdminFilterDTO;
import api.giybat.uz.dto.profile.ProfileUpdateDetailDTO;
import api.giybat.uz.dto.profile.ProfileUpdatePasswordDTO;
import api.giybat.uz.dto.profile.ProfileUpdateUsernameDTO;
import api.giybat.uz.enums.AppLanguage;
import api.giybat.uz.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PutMapping("/detail")
    public ResponseEntity<ApiResponse<String>> updateDetail(@Valid @RequestBody ProfileUpdateDetailDTO profileDTO,
                                                            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        ApiResponse<String> apiResponse = profileService.updateDetail(profileDTO, language);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/update/password")
    public ResponseEntity<ApiResponse<String>> updatePassword(@Valid @RequestBody ProfileUpdatePasswordDTO profileDTO,
                                                              @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        ApiResponse<String> apiResponse = profileService.updatePassword(profileDTO, language);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/update/username")
    public ResponseEntity<ApiResponse<String>> updateUsername(@Valid @RequestBody ProfileUpdateUsernameDTO profileDTO,
                                                              @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        ApiResponse<String> apiResponse = profileService.updateUsername(profileDTO, language);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/update/confirm")
    public ResponseEntity<ApiResponse<String>> updateConfirm(@Valid @RequestBody CodeConfirmDTO dto,
                                                             @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        ApiResponse<String> apiResponse = profileService.updateUsernameConfirm(dto, language);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/filter")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<PageImpl<PostDTO>>> filter(@RequestBody ProfileAdminFilterDTO filterDTO,
                                                                 @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                 @RequestParam(value = "size", defaultValue = "10") Integer size) {
        ApiResponse<PageImpl<PostDTO>> postResponse = profileService.adminFilter(filterDTO, page - 1, size);
        return ResponseEntity.ok(postResponse);
    }
}
