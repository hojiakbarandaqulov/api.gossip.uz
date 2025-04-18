package api.giybat.uz.repository;

import api.giybat.uz.entity.ProfileRoleEntity;
import api.giybat.uz.enums.ProfileRole;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileRoleRepository extends CrudRepository<ProfileRoleEntity, Integer> {
    @Transactional
    @Modifying
    void deleteByProfileId(Integer profileId);

    @Query("select p.roles from ProfileRoleEntity p where p.profileId=?1")
    List<ProfileRole> getAllRolesListByProfileId(Integer profileId);

    @NotNull
    Optional<ProfileRoleEntity> findById(@NotNull Integer id);
}
