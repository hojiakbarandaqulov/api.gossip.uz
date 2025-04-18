package api.giybat.uz.controller;

import api.giybat.uz.dto.ApiResponse;
import api.giybat.uz.dto.ProfileDTO;
import api.giybat.uz.dto.profile.ProfileUpdateDetailDTO;
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

    @PostMapping("/detail")
    public ResponseEntity<ApiResponse<String>> detail(@Valid @RequestBody ProfileUpdateDetailDTO profileDTO,
                                                      @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        ApiResponse<String> apiResponse = profileService.updateDetail(profileDTO, language);
        return ResponseEntity.ok(apiResponse);
    }


}
