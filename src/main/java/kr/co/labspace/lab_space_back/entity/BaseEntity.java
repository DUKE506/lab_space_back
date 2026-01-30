package kr.co.labspace.lab_space_back.entity;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class BaseEntity {
    @CreatedDate  // ← 추가!
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy  // ← 추가!
    @Column(name = "created_user", updatable = false)
    private String createdUser;

    @LastModifiedDate  // ← 추가!
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @LastModifiedBy  // ← 추가!
    @Column(name = "updated_user")
    private String updatedUser;


    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "deleted_user")
    private String deleteUser;

    // Soft Delete 메서드
    public void delete(String deletedBy){
        this.deletedAt = LocalDateTime.now();
        this.deleteUser = deletedBy;
    }
}
