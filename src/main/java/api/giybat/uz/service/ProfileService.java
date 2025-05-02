package api.giybat.uz.service;

import api.giybat.uz.dto.ApiResponse;
import api.giybat.uz.dto.confirm.CodeConfirmDTO;
import api.giybat.uz.dto.post.PostAdminFilterDTO;
import api.giybat.uz.dto.post.PostDTO;
import api.giybat.uz.dto.profile.ProfileUpdateDetailDTO;
import api.giybat.uz.dto.profile.ProfileUpdatePasswordDTO;
import api.giybat.uz.dto.profile.ProfileUpdateUsernameDTO;
import api.giybat.uz.entity.ProfileEntity;
import api.giybat.uz.enums.AppLanguage;
import api.giybat.uz.enums.ProfileRole;
import api.giybat.uz.exps.AppBadException;
import api.giybat.uz.repository.ProfileRepository;
import api.giybat.uz.repository.ProfileRoleRepository;
import api.giybat.uz.service.sms.EmailSendingService;
import api.giybat.uz.service.sms.SmsService;
import api.giybat.uz.util.EmailUtil;
import api.giybat.uz.util.JwtUtil;
import api.giybat.uz.util.PhoneUtil;
import api.giybat.uz.util.SpringSecurityUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileRoleRepository profileRoleRepository;
    private final SmsService smsService;
    private final ResourceBundleService bundleService;
    private final EmailSendingService emailSendingService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailHistoryService emailHistoryService;
    private final SmsHistoryService smsHistoryService;

    public ProfileService(ProfileRepository profileRepository, ProfileRoleRepository profileRoleRepository, SmsService smsService, ResourceBundleService bundleService, EmailSendingService emailSendingService, BCryptPasswordEncoder bCryptPasswordEncoder, EmailHistoryService emailHistoryService, SmsHistoryService smsHistoryService) {
        this.profileRepository = profileRepository;
        this.profileRoleRepository = profileRoleRepository;
        this.smsService = smsService;
        this.bundleService = bundleService;
        this.emailSendingService = emailSendingService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailHistoryService = emailHistoryService;
        this.smsHistoryService = smsHistoryService;
    }

    public ApiResponse<String> updateDetail(ProfileUpdateDetailDTO updateDetailDTO,
                                            AppLanguage language) {
        Integer profileId = SpringSecurityUtil.getProfileId();
        profileRepository.updateDetail(profileId, updateDetailDTO.getName());
        return new ApiResponse<>(bundleService.getMessage("profile.detail.update.success", language));
    }

    public ApiResponse<String> updatePassword(ProfileUpdatePasswordDTO profileDTO, AppLanguage language) {
        Integer profileId = SpringSecurityUtil.getProfileId();
        ProfileEntity profile = getById(profileId);
        if (bCryptPasswordEncoder.matches(profileDTO.getCurrentPassword(), profile.getPassword())) {
            throw new AppBadException(bundleService.getMessage("wrong.password", language));
        }
        profileRepository.updatePassword(profileId, profileDTO.getCurrentPassword());
        return new ApiResponse<>(bundleService.getMessage("profile.password.update.success", language));
    }

    public ApiResponse<String> updateUsername(ProfileUpdateUsernameDTO profileDTO, AppLanguage language) {
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(profileDTO.getUsername());
        if (optional.isPresent()) {
            return new ApiResponse<>(bundleService.getMessage("email.phone.exists", language));
        }
        if (PhoneUtil.isPhone(profileDTO.getUsername())) {
            smsService.sendSms(profileDTO.getUsername());
        } else if (EmailUtil.isEmail(profileDTO.getUsername())) {
            emailSendingService.sentResetPasswordEmail(profileDTO.getUsername(), language);
        }
        Integer profileId = SpringSecurityUtil.getProfileId();
        profileRepository.updateTempUsername(profileId, profileDTO.getUsername());
        return new ApiResponse<>(bundleService.getMessage("reset.password.response", language));
    }

    public ApiResponse<String> updateUsernameConfirm(CodeConfirmDTO dto, AppLanguage language) {
        Integer profileId = SpringSecurityUtil.getProfileId();
        ProfileEntity profile = getById(profileId);
        String tempUsername = profile.getTempUsername();
        if (PhoneUtil.isPhone(tempUsername)) {
            smsHistoryService.checkSmsCode(tempUsername, dto.getCode(), language);
        } else if (EmailUtil.isEmail(tempUsername)) {
            emailHistoryService.check(tempUsername, dto.getCode(), language);
        }
        profileRepository.updateUsername(profileId, tempUsername);
        List<ProfileRole> roles = profileRoleRepository.getAllRolesListByProfileId(profile.getId());
        String jwt = JwtUtil.encode(tempUsername, profile.getId(), roles);
        return new ApiResponse<>(jwt, bundleService.getMessage("change.username.success", language));
    }

    public ProfileEntity getById(Integer id) {
        return profileRepository.findByIdAndVisibleTrue(id).orElseThrow(() -> new AppBadException("Profile not found"));
    }

    public List<PostDTO> adminFilter(PostAdminFilterDTO filterDTO) {

    }
}
