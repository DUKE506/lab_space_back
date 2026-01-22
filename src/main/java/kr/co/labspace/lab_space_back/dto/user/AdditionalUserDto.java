package kr.co.labspace.lab_space_back.dto.user;


import kr.co.labspace.lab_space_back.entity.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdditionalUserDto {
    private Long id;
    private String phone;
    private String university;
    private String department;
    private UserType userType;

}
