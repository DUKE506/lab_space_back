package kr.co.labspace.lab_space_back.service;

import kr.co.labspace.lab_space_back.dto.LoginRequestDto;
import kr.co.labspace.lab_space_back.dto.LoginResponseDto;
import kr.co.labspace.lab_space_back.entity.User;
import kr.co.labspace.lab_space_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    public LoginResponseDto login(LoginRequestDto loginDto){
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
}
