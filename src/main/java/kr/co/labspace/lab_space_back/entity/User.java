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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private UserType userType = UserType.GUEST; // 기본 게스트;

    @Column(nullable = true)
    private String providerId; // kakaoId 등을 저장

    private String name;
    private String nickname; // 카카오 닉네임
    private String profileImage; //카카오 프로필 이미지
    private String phone; // 전화번호
    private String university; // 대학명
    private String department; // 학과명
    private Integer studentId; // 학번
    @Column(name = "admission_dt")
    private LocalDateTime admissionDt; // 학생전용
    @Column(name = "graduation_dt")
    private LocalDateTime graduationDt; // 학생전용
    private String grade;

    @Column(nullable = false)
    private Boolean isProfileCompleted = false;

    // 프로필 완성도 확인 -> 완성해야 연구실 생성 할 수 있음
    public boolean canComplete (){
        return phone !=null && name !=null && university !=null && department != null;
    }

    // OAuth 로그인시 프로필 정보 추가 입력
    public void updateAdditionalInfo ( String phone,String name, String university, String department){
        this.phone = phone;
        this.name = name;
        this.university = university;
        this.department = department;

    }


}
