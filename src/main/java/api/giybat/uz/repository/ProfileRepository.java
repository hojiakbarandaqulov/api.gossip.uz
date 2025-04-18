package api.giybat.uz.repository;

import api.giybat.uz.entity.ProfileEntity;
import api.giybat.uz.enums.GeneralStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {

    Optional<ProfileEntity> findByUsernameAndVisibleTrue(String email);

    Optional<ProfileEntity> findByIdAndVisibleTrue(Integer id);

    @Modifying
    @Transactional
    @Query("update ProfileEntity  set status=?2 where id=?1")
    void changeStatus(Integer id, GeneralStatus status);

    @Modifying
    @Transactional
    @Query("update ProfileEntity set password=?2 where id =?1")
    void updatePassword(Integer id, String password);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set name=?2 where id=?1")
    void updateDetail(Integer id, String name);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set tempUsername=?2 where id=?1")
    void updateTempUsername(Integer id, String tempUsername);


    @Transactional
    @Modifying
    @Query("update ProfileEntity  set username=?2 where id=?1")
    void updateUsername(Integer profileId, String tempUsername);
}
