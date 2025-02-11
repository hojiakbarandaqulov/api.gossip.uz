package api.giybat.uz.service;

import api.giybat.uz.Application;
import api.giybat.uz.config.ResourceBundleConfig;
import api.giybat.uz.dto.*;
import api.giybat.uz.entity.ProfileEntity;
import api.giybat.uz.enums.AppLanguage;
import api.giybat.uz.enums.GeneralStatus;
import api.giybat.uz.enums.ProfileRole;
import api.giybat.uz.exps.AppBadException;
import api.giybat.uz.repository.ProfileRepository;
import api.giybat.uz.repository.ProfileRoleRepository;
import api.giybat.uz.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class AuthService {
    private final ProfileRepository profileRepository;
    private final EmailSendingService emailSendingService;
    private final ProfileService profileService;
    private final ProfileRoleRepository profileRoleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ProfileRoleService profileRoleService;
    private final ResourceBundleService messageService;

    public AuthService(ProfileRepository profileRepository, EmailSendingService emailSendingService, ProfileService profileService, ProfileRoleRepository profileRoleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ProfileRoleService profileRoleService, ResourceBundleService messageService) {
        this.profileRepository = profileRepository;
        this.emailSendingService = emailSendingService;
        this.profileService = profileService;
        this.profileRoleRepository = profileRoleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.profileRoleService = profileRoleService;
        this.messageService = messageService;
    }

    public ApiResponse<String> registration(RegistrationDTO dto, AppLanguage language) {
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(dto.getUsername());
        if (optional.isPresent()) {
            ProfileEntity profileEntity = optional.get();
            if (profileEntity.getStatus().equals(GeneralStatus.IN_REGISTRATION)) {
                profileRoleService.deleteRoles(profileEntity.getId());
                profileRepository.delete(profileEntity);
            } else {
                throw new AppBadException(messageService.getMessage("email.phone.exists", language));
            }
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setUsername(dto.getUsername());
        entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        entity.setStatus(GeneralStatus.IN_REGISTRATION);
        entity.setVisible(true);
        entity.setCreatedDate(LocalDateTime.now());
        profileRepository.save(entity);

        profileRoleService.create(entity.getId(), ProfileRole.ROLE_USER);

        emailSendingService.sendRegistrationEmail(dto.getUsername(), entity.getId(), language);
        return ApiResponse.ok(messageService.getMessage("email.confirm.send", language));
    }

    public ApiResponse<String> regVerification(String token, AppLanguage language) {
        try {
            Integer profileId = JwtUtil.decode(token).getId();
            ProfileEntity profile = profileService.getById(profileId);
            if (profile.getStatus().equals(GeneralStatus.IN_REGISTRATION)) {
                profileRepository.changeStatus(profileId, GeneralStatus.ACTIVE);
                return ApiResponse.ok(messageService.getMessage("registration.successful", language));
            }
        } catch (JwtException e) {
        }
        throw new AppBadException(messageService.getMessage("verification.wrong", language));
    }

    public ApiResponse<ProfileDTO> login(LoginDTO loginDTO, AppLanguage language) {
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(loginDTO.getUsername());
        if (optional.isEmpty()) {
            throw new AppBadException(messageService.getMessage("username.password.wrong", language));
        }
        ProfileEntity profile = optional.get();
        if (!bCryptPasswordEncoder.matches(loginDTO.getPassword(), profile.getPassword())) {
            throw new AppBadException(messageService.getMessage("wrong.password", language));
        }
        if (!profile.getStatus().equals(GeneralStatus.ACTIVE)) {
            throw new AppBadException(messageService.getMessage("wrong.status", language));
        }
        ProfileDTO response = new ProfileDTO();
        response.setName(profile.getName());
        response.setUsername(profile.getUsername());
        response.setRole(profileRoleRepository.getAllRolesListByProfileId(profile.getId()));
        response.setJwt(JwtUtil.encode(profile.getUsername(), profile.getId(), response.getRole()));
        return ApiResponse.ok(response);
    }

    public ApiResponse<ProfileDTO> login(LoginDTO loginDTO){
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(loginDTO.getUsername());
        if (optional.isEmpty()){
            throw new AppBadException("Username email or password is wrong");
        }
        ProfileEntity profile = optional.get();
        if (bCryptPasswordEncoder.matches(loginDTO.getPassword(), profile.getPassword())){
            throw new AppBadException("Wrong password");
        }
        if (!profile.getStatus().equals(GeneralStatus.ACTIVE)){
            throw new AppBadException("Wrong status");
        }
    return null;
    }

}