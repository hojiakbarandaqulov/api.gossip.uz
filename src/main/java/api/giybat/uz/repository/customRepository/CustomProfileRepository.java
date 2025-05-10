package api.giybat.uz.repository.customRepository;

import api.giybat.uz.dto.FilterResultDTO;
import api.giybat.uz.dto.profile.ProfileAdminFilterDTO;
import api.giybat.uz.entity.ProfileEntity;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class CustomProfileRepository {
    private EntityManager entityManager;

    public CustomProfileRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public FilterResultDTO<ProfileEntity> filterPagination(ProfileAdminFilterDTO profileAdminFilterDTO,
                                                           Integer page, Integer size) {
        return null;
    }
}
