package api.giybat.uz.repository;

import api.giybat.uz.entity.SmsHistoryEntity;
import api.giybat.uz.enums.SmsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SmsHistoryRepository extends JpaRepository<SmsHistoryEntity,Integer> {

    Long countByPhoneAndCreatedDateBetween(String phone, LocalDateTime from, LocalDateTime to);

    @Query("SELECT s FROM SmsHistoryEntity s WHERE s.phone = ?1 ORDER BY s.createdDate DESC")
    Optional<SmsHistoryEntity> findTopByPhoneOrderByCreatedDateDesc(String phone);

    @Modifying
    @Transactional
    @Query("update SmsHistoryEntity set status = ?2 where id = ?1")
    void updateStatus(Long id, SmsType status);
}
