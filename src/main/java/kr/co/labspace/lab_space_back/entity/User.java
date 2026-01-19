package kr.co.labspace.lab_space_back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = true)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private AuthProvider provider = AuthProvider.LOCAL; //기본값 LOCAL

    @Column(nullable = true)
    private String providerId; // kakaoId 등을 저장

    private String name;
    private String nickname; // 카카오 닉네임
    private String profileImage; //카카오 프로필 이미지
    private String phone;
    private String university;
    private String department;
    @Column(name = "admission_dt")
    private LocalDateTime admissionDt;
    @Column(name = "graduation_dt")
    private LocalDateTime graduationDt;
    private String grade;

}
