package api.giybat.uz.repository;

import api.giybat.uz.entity.ProfileEntity;
import api.giybat.uz.enums.GeneralStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {

    Optional<ProfileEntity> findByEmailAndVisibleTrue(String email);

    Optional<ProfileEntity> findByIdAndVisibleTrue(Integer id);

    @Modifying
    @Transactional
    @Query("update ProfileEntity  set status=?2 where id=?1")
    void changeStatus(Integer id,GeneralStatus status);
}
