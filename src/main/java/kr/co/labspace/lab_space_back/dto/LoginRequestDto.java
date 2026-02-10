package kr.co.labspace.lab_space_back.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "password")
public class LoginRequestDto {
    private String email;
    private String password;
}
