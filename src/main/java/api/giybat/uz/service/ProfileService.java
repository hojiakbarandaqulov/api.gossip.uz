package api.giybat.uz.service;

import api.giybat.uz.dto.ApiResponse;
import api.giybat.uz.dto.profile.ProfileUpdateDetailDTO;
import api.giybat.uz.dto.profile.ProfileUpdatePasswordDTO;
import api.giybat.uz.entity.ProfileEntity;
import api.giybat.uz.enums.AppLanguage;
import api.giybat.uz.exps.AppBadException;
import api.giybat.uz.repository.ProfileRepository;
import api.giybat.uz.util.SpringSecurityUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public ProfileService(ProfileRepository profileRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.profileRepository = profileRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ApiResponse<String> updateDetail(ProfileUpdateDetailDTO updateDetailDTO,
                                            AppLanguage language){
        Integer profileId= SpringSecurityUtil.getProfileId();
        profileRepository.updateDetail(profileId,updateDetailDTO.getName());
        return new ApiResponse<>("profile.detail.update.success",language);
    }

    public ApiResponse<String> updatePassword(ProfileUpdatePasswordDTO profileDTO, AppLanguage language) {
        Integer profileId = SpringSecurityUtil.getProfileId();
        ProfileEntity profile=getById(profileId);
        if (bCryptPasswordEncoder.matches(profileDTO.getCurrentPassword(),profile.getPassword())){
            throw new AppBadException("wrong.password");
        }
        profileRepository.updatePassword(profileId, profileDTO.getCurrentPassword());
        return new ApiResponse<>("profile.password.update.success",language);
    }

    public ProfileEntity getById(Integer id) {
        return profileRepository.findByIdAndVisibleTrue(id).orElseThrow(() -> new AppBadException("Profile not found"));
    }
}
