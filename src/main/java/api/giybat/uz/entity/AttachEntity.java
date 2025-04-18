package api.giybat.uz.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.*;

@Data
@Entity
@Table(name = "attach")
public class AttachEntity {
    @Id
    private String id;

    @Column(name = "original_name", length = 255)
    private String originalName;

    @Column(name = "size")
    private Long size;

    @Column(name = "extension")
    private String extension;

    @Column(name = "path")
    private String path;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

}
