package api.giybat.uz.service;

import api.giybat.uz.dto.*;
import api.giybat.uz.entity.ProfileEntity;
import api.giybat.uz.enums.GeneralStatus;
import api.giybat.uz.enums.ProfileRole;
import api.giybat.uz.exps.AppBadException;
import api.giybat.uz.repository.ProfileRepository;
import api.giybat.uz.repository.ProfileRoleRepository;
import api.giybat.uz.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    private final ProfileRepository profileRepository;
    private final EmailSendingService emailSendingService;
    private final ProfileService profileService;
    private final ProfileRoleRepository profileRoleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ProfileRoleService profileRoleService;

    public AuthService(ProfileRepository profileRepository, EmailSendingService emailSendingService, ProfileService profileService, ProfileRoleRepository profileRoleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ProfileRoleService profileRoleService) {
        this.profileRepository = profileRepository;
        this.emailSendingService = emailSendingService;
        this.profileService = profileService;
        this.profileRoleRepository = profileRoleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.profileRoleService = profileRoleService;
    }

    public ApiResponse<String> registration(RegistrationDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(dto.getUsername());
        if (optional.isPresent()) {
            ProfileEntity profileEntity = optional.get();
            if (profileEntity.getStatus().equals(GeneralStatus.IN_REGISTRATION)) {
                profileRoleService.deleteRoles(profileEntity.getId());
                profileRepository.delete(profileEntity);
            } else {
                throw new AppBadException("Email already in use");
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

        emailSendingService.sendRegistrationEmail(dto.getUsername(), entity.getId(), Collections.singletonList(ProfileRole.ROLE_USER));
        return ApiResponse.ok("Registration successful");
    }

    public ApiResponse<String> regVerification(String token) {
        try {
            Integer profileId = JwtUtil.decode(token).getId();
            ProfileEntity profile = profileService.getById(profileId);
            if (profile.getStatus().equals(GeneralStatus.IN_REGISTRATION)) {
                profileRepository.changeStatus(profileId, GeneralStatus.ACTIVE);
                return ApiResponse.ok("Registration successful");
            }
        } catch (JwtException e) {
        }
        throw new AppBadException("Verification failed");
    }

    public ApiResponse<ProfileDTO> login(LoginDTO loginDTO) {
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(loginDTO.getUsername());
        if (optional.isEmpty()) {
            throw new AppBadException("Username email or password is wrong");
        }
        ProfileEntity profile = optional.get();
        if (!bCryptPasswordEncoder.matches(loginDTO.getPassword(), profile.getPassword())) {
            throw new AppBadException("Wrong password");
        }
        if (!profile.getStatus().equals(GeneralStatus.ACTIVE)) {
            throw new AppBadException("Wrong status");
        }
        ProfileDTO response = new ProfileDTO();
        response.setName(profile.getName());
        response.setUsername(profile.getUsername());
        response.setRole(profileRoleRepository.getAllRolesListByProfileId(profile.getId()));
        response.setJwt(JwtUtil.encode(profile.
                getUsername(), profile.getId(), response.getRole()));
        return ApiResponse.ok(response);
    }
}