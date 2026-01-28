package kr.co.labspace.lab_space_back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "labs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lab {
    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //연구실명
    private String name;

    //승인상태
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;



    //교수


}
