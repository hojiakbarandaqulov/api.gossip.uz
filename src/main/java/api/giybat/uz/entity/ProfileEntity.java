package api.giybat.uz.entity;

import api.giybat.uz.enums.GeneralStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Table(name = "profile")
@Entity
@Data
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private GeneralStatus status;
    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
