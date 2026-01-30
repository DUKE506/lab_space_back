package kr.co.labspace.lab_space_back.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "lab_members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LabMember extends BaseEntity{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private LabRole labRole;

    private ApprovalStatus approvalStatus;

    @Column(name = "joined_at")
    private LocalDateTime joinedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "lab_id")
    @JsonBackReference
    private Lab lab;

}
