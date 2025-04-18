package api.giybat.uz.service;

import api.giybat.uz.dto.ApiResponse;
import api.giybat.uz.dto.profile.ProfileUpdateDetailDTO;
import api.giybat.uz.dto.profile.ProfileUpdatePasswordDTO;
import api.giybat.uz.dto.profile.ProfileUpdateUsernameDTO;
import api.giybat.uz.entity.ProfileEntity;
import api.giybat.uz.enums.AppLanguage;
import api.giybat.uz.exps.AppBadException;
import api.giybat.uz.repository.ProfileRepository;
import api.giybat.uz.service.sms.EmailSendingService;
import api.giybat.uz.service.sms.SmsService;
import api.giybat.uz.util.EmailUtil;
import api.giybat.uz.util.PhoneUtil;
import api.giybat.uz.util.SpringSecurityUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final SmsService smsService;
    private final EmailSendingService emailSendingService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public ProfileService(ProfileRepository profileRepository, SmsService smsService, EmailSendingService emailSendingService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.profileRepository = profileRepository;
        this.smsService = smsService;
        this.emailSendingService = emailSendingService;
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

    public ApiResponse<String> updateUsername(ProfileUpdateUsernameDTO profileDTO, AppLanguage language) {
        Optional<ProfileEntity> optional=profileRepository.findByUsernameAndVisibleTrue(profileDTO.getUsername());
        if (optional.isPresent()){
            return new ApiResponse<>("email.phone.exists",language);
        }
        if (PhoneUtil.isPhone(profileDTO.getUsername())){
            smsService.sendSms(profileDTO.getUsername());
        } else if (EmailUtil.isEmail(profileDTO.getUsername()) ){
            emailSendingService.sentResetPasswordEmail(profileDTO.getUsername(), language);
        }
        Integer profileId = SpringSecurityUtil.getProfileId();
        profileRepository.updateTempUsername(profileId, profileDTO.getUsername());
        return new ApiResponse<>("reset.password.response",language);
    }
}
