package kr.co.labspace.lab_space_back.dto;


import kr.co.labspace.lab_space_back.entity.User;
import kr.co.labspace.lab_space_back.entity.UserType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private String phone;
    private String university;
    private String department;
    private UserType userType;
    private Boolean isProfileCompleted;
    // password 제외!

    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .university(user.getUniversity())
                .department(user.getDepartment())
                .userType(user.getUserType())
                .isProfileCompleted(user.getIsProfileCompleted())
                .build();
    }
}