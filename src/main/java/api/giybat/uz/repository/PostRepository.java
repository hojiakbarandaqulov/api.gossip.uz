package api.giybat.uz.repository;

import api.giybat.uz.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, String> {


    List<PostEntity> getAllByProfileIdAndVisibleTrue(Integer id);
}
