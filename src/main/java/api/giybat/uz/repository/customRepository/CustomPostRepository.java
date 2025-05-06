package api.giybat.uz.repository.customRepository;

import api.giybat.uz.dto.FilterResultDTO;
import api.giybat.uz.dto.post.PostFilterDTO;
import api.giybat.uz.entity.PostEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomPostRepository {
    private EntityManager entityManager;

    public CustomPostRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public FilterResultDTO<PostEntity> filter(PostFilterDTO filterDTO, int page, int size) {
        StringBuilder queryBuilder = new StringBuilder(" where visible = true ");

        Map<String, Object> params = new HashMap<String, Object>();

        if (filterDTO.getQuery() != null) {
            queryBuilder.append(" and lower(p.like) like : query) ");
            params.put("query", filterDTO.getQuery().toLowerCase() + "%");
        }

        queryBuilder.append(" order by p.created_date desc ");

        StringBuilder selectBuilder = new StringBuilder("Select p from PostEntity p ")
                .append(queryBuilder);

        StringBuilder counrBuilder = new StringBuilder("Select count(p) from PostEntity p ")
                .append(queryBuilder);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        selectQuery.setFirstResult((page - 1) * size);
        selectQuery.setMaxResults(size);
        params.forEach(selectQuery::setParameter);
        List<PostEntity> entityList = selectQuery.getResultList();

        Query countQuery = entityManager.createQuery(queryBuilder.toString());
        params.forEach(countQuery::setParameter);
        Long totalCount = (Long) countQuery.getSingleResult();

        return new FilterResultDTO<PostEntity>(entityList,totalCount);
    }

}
