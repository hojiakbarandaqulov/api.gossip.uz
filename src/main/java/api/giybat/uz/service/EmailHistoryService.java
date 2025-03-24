package api.giybat.uz.service;

import api.giybat.uz.entity.EmailHistoryEntity;
import api.giybat.uz.enums.AppLanguage;
import api.giybat.uz.enums.SmsType;
import api.giybat.uz.exps.AppBadException;
import api.giybat.uz.repository.EmailHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@Service
public class EmailHistoryService {
    private final EmailHistoryRepository emailHistoryRepository;
    private final ResourceBundleService bundleService;

    public void create(String email, String code, SmsType emailType){
        EmailHistoryEntity emailHistoryEntity = new EmailHistoryEntity();
        emailHistoryEntity.setEmail(email);
        emailHistoryEntity.setCode(code);
        emailHistoryEntity.setEmailType(emailType);
        emailHistoryEntity.setAttemptCount(0);
        emailHistoryEntity.setCreatedDate(LocalDateTime.now());
        emailHistoryRepository.save(emailHistoryEntity);
    }

    public Long getEmailCount(String email){
        LocalDateTime now = LocalDateTime.now();
        return emailHistoryRepository.countByEmailAndCreatedDateBetween(email,now.minusMinutes(1),now);
    }

    public void check(String email, String code, AppLanguage language){
        Optional<EmailHistoryEntity> optional = emailHistoryRepository.findTop1ByEmailOrderByCreatedDateDesc(email);

        if(optional.isEmpty()){
           throw  new AppBadException(bundleService.getMessage("verification.failed",language));
        }
        EmailHistoryEntity entity = optional.get();

        if (entity.getAttemptCount()>=3){
            throw  new AppBadException(bundleService.getMessage("verification.failed",language));
        }

        if (!entity.getCode().equals(code)){
            emailHistoryRepository.updateAttemptCount(entity.getId());
            throw  new AppBadException(bundleService.getMessage("verification.failed",language));
        }

        LocalDateTime expDate=entity.getCreatedDate().plusMinutes(2);
        if (LocalDateTime.now().isAfter(expDate)){
            throw  new AppBadException(bundleService.getMessage("verification.failed",language));
        }
    }
}
