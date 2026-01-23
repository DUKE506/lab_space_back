package kr.co.labspace.lab_space_back.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "files")
@Getter
@Setter
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private String originalFilename;

    @Column(nullable = false)
    private String savedFilename;

    @Column(nullable = false)
    private Long fileSize;

    @Column(nullable = false)
    private String contentType;  // MIME 타입 (image/jpeg, application/pdf 등)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FileCategory category;  // LAB, USER, PROJECT 등

    private Long referenceId; // 참조하는 엔티티ID

    @CreatedDate
    private LocalDateTime uploadedAt;

    @Column(nullable = false)
    private Boolean deleted =false;


}
