package kr.co.labspace.lab_space_back.dto.user;

import kr.co.labspace.lab_space_back.dto.UserDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpResponseDto {
    private UserDto user;  // password 없음
    private String accessToken;
}
