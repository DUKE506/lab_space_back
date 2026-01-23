package kr.co.labspace.lab_space_back.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "labs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lab {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //연구실명
    private String name;

    //승인 상태
    private ApprovalStatus status = ApprovalStatus.PENDING; //기본값 대기

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private User professor; //일단 교수 : 연구실 = 1 : N


}
