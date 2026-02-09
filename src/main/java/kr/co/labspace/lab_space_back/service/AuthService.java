package kr.co.labspace.lab_space_back.service;

import kr.co.labspace.lab_space_back.dto.LoginRequestDto;
import kr.co.labspace.lab_space_back.dto.LoginResponseDto;
import kr.co.labspace.lab_space_back.dto.SignUpRequestDto;
import kr.co.labspace.lab_space_back.dto.UserDto;
import kr.co.labspace.lab_space_back.dto.user.SignUpResponseDto;
import kr.co.labspace.lab_space_back.entity.User;
import kr.co.labspace.lab_space_back.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    public LoginResponseDto login(LoginRequestDto loginDto){
        log.info("이메일 : {} ", loginDto.getEmail());
        // 1. 이메일로 사용자 찾기
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new RuntimeException(("사용자를 찾을 수 없습니다.")));

        // 2. 비밀번호 확인
        if(loginDto.getPassword() != user.getPassword()){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 3. 토큰 생성 (JWT는 나중에 구현)
        String token = "temporary-token-" + user.getId();

        return new LoginResponseDto(token, user.getEmail(), user.getName());
    }


    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto){
        User user = User.builder()
                .name(signUpRequestDto.getName())
                .nickname(signUpRequestDto.getName())
                .email(signUpRequestDto.getEmail())
                .password(signUpRequestDto.getPassword())
                .university(signUpRequestDto.getUniversity())
                .department(signUpRequestDto.getDepartment())
                .userType(signUpRequestDto.getUserType())
                .isProfileCompleted(true)
                .build();

        User savedUser = userRepository.save(user);


        return SignUpResponseDto.builder()
                .user(UserDto.from(savedUser))
                .accessToken("asdadasd")
                .build();

    }
}
