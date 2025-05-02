package api.giybat.uz.repository.customRepository;

import api.giybat.uz.dto.post.PostFilterDTO;
import jakarta.persistence.EntityManager;

public class CustomPostRepository {
    private EntityManager entityManager;

    public CustomPostRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
