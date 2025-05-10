package api.giybat.uz.dto.profile;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ProfileAdminFilterDTO {
    private String name;
    private String username;
    private LocalDate createdDateTo;
    private LocalDate createdDateFrom;
}
