package kr.co.labspace.lab_space_back.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.List;


@Entity
@Table(name = "labs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lab extends BaseEntity{
    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //연구실명
    private String name;

    //승인상태
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;

    //가입 코드
    private String code;

    //모집여부
    private Boolean isRecruiting;


    //멤버
    @OneToMany(mappedBy = "lab",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<LabMember> labMembers;


}
