package api.giybat.uz.config;

import api.giybat.uz.entity.ProfileEntity;
import api.giybat.uz.enums.ProfileRole;
import api.giybat.uz.repository.ProfileRepository;
import api.giybat.uz.repository.ProfileRoleRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ProfileRepository profileRepository;
    private final ProfileRoleRepository profileRoleRepository;

    public CustomUserDetailsService(ProfileRepository profileRepository, ProfileRoleRepository profileRoleRepository) {
        this.profileRepository = profileRepository;
        this.profileRoleRepository = profileRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(username);
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }
        ProfileEntity profile = optional.get();
        List<ProfileRole> roleList=profileRoleRepository.getAllRolesListByProfileId(profile.getId());
        return new CustomUserDetails(profile,roleList);
    }
}
