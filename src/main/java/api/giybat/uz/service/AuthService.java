package api.giybat.uz.service;

import api.giybat.uz.dto.RegistrationDTO;
import api.giybat.uz.entity.ProfileEntity;
import api.giybat.uz.exps.AppBadException;
import api.giybat.uz.repository.ProfileRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final ProfileRepository profileRepository;

    public AuthService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public String registration(RegistrationDTO dto){
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(dto.getEmail());
        if (optional.isPresent()) {
            throw new AppBadException("Email already in use");
        }
        return null;
    }
}
