package kr.co.labspace.lab_space_back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    private String name;
    private String phone;
    private String university;
    private String department;
    @Column(name = "admission_dt")
    private LocalDateTime admissionDt;
    @Column(name = "graduation_dt")
    private LocalDateTime graduationDt;
    private String grade;



}
