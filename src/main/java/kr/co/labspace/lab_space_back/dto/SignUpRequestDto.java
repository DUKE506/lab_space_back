package kr.co.labspace.lab_space_back.dto;

import kr.co.labspace.lab_space_back.entity.UserType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "password")
public class SignUpRequestDto {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String university;
    private String department;
    private UserType userType;
}
