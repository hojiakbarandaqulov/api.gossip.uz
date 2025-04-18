package api.giybat.uz.controller;

import api.giybat.uz.dto.ApiResponse;
import api.giybat.uz.dto.ProfileDTO;
import api.giybat.uz.dto.profile.ProfileUpdateDetailDTO;
import api.giybat.uz.dto.profile.ProfileUpdatePasswordDTO;
import api.giybat.uz.dto.profile.ProfileUpdateUsernameDTO;
import api.giybat.uz.enums.AppLanguage;
import api.giybat.uz.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
