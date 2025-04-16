package api.giybat.uz.service;

import api.giybat.uz.dto.ApiResponse;
import api.giybat.uz.dto.profile.ProfileUpdateDetailDTO;
import api.giybat.uz.entity.ProfileEntity;
import api.giybat.uz.enums.AppLanguage;
import api.giybat.uz.exps.AppBadException;
import api.giybat.uz.repository.ProfileRepository;
import api.giybat.uz.util.SpringSecurityUtil;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public ApiResponse<String> updateDetail(ProfileUpdateDetailDTO updateDetailDTO,
                                            AppLanguage language){
        Integer profileId= SpringSecurityUtil.getCurrentUserId();
        profileRepository.updateDetail(profileId,updateDetailDTO.getName());

        return new ApiResponse<>("");
    }


    public ProfileEntity getById(Integer id) {
        return profileRepository.findByIdAndVisibleTrue(id).orElseThrow(() -> new AppBadException("Profile not found"));
    }

}
