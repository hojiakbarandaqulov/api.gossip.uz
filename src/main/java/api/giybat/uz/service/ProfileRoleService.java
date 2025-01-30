package api.giybat.uz.service;

import api.giybat.uz.entity.ProfileRoleEntity;
import api.giybat.uz.enums.ProfileRole;
import api.giybat.uz.repository.ProfileRoleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProfileRoleService {
    private final ProfileRoleRepository profileRoleRepository;

    public ProfileRoleService(ProfileRoleRepository profileRoleRepository) {
        this.profileRoleRepository = profileRoleRepository;
    }
    public void create(Integer profileId, ProfileRole role) {
        ProfileRoleEntity profileRoleEntity = new ProfileRoleEntity();
        profileRoleEntity.setProfileId(profileId);
        profileRoleEntity.setRoles(role);
        profileRoleEntity.setCreatedDate(LocalDateTime.now());
        profileRoleRepository.save(profileRoleEntity);
    }

    public void deleteRoles(Integer profileId) {
        profileRoleRepository.deleteByProfileId(profileId);
    }
}
