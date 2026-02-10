package kr.co.labspace.lab_space_back.service;

import kr.co.labspace.lab_space_back.dto.LoginRequestDto;
import kr.co.labspace.lab_space_back.dto.LoginResponseDto;
import kr.co.labspace.lab_space_back.dto.SignUpRequestDto;
import kr.co.labspace.lab_space_back.dto.UserDto;
import kr.co.labspace.lab_space_back.entity.AuthProvider;
import kr.co.labspace.lab_space_back.entity.User;
import kr.co.labspace.lab_space_back.global.exception.AuthProviderMismatchException;
import kr.co.labspace.lab_space_back.global.exception.DuplicateEmailException;
import kr.co.labspace.lab_space_back.global.exception.InValidPasswordException;
import kr.co.labspace.lab_space_back.global.exception.UserNotFoundException;
import kr.co.labspace.lab_space_back.repository.UserRepository;
import kr.co.labspace.lab_space_back.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(PasswordEncoder passwordEncoder,JwtTokenProvider jwtTokenProvider){
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public LoginResponseDto login(LoginRequestDto loginDto){
        log.info("이메일 : {} ", loginDto.getEmail());
        // 1. 이메일로 사용자 찾기
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException());

        // 2. 로그인 AuthProvider 확인
        if(user.getProvider() == AuthProvider.KAKAO){
            throw new AuthProviderMismatchException();
        }

        // 3. 비밀번호 확인
        if(!passwordEncoder.matches(loginDto.getPassword(),user.getPassword())  ){
            throw new InValidPasswordException();
        }

        // 3. 토큰 생성 (JWT는 나중에 구현)

        String accessToken = jwtTokenProvider.createAccessToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);


        return new LoginResponseDto(accessToken,refreshToken,UserDto.from(user));
    }


    public void signUp(SignUpRequestDto signUpRequestDto){

        //이메일 중복 검사
        Optional<User> existingUser = userRepository.findByEmail(signUpRequestDto.getEmail());

        //휴대폰 중복 검사 - 테스트 서버이기에 풀어놓음
//        Optional<User> existingPhone = userRepository.findByPhone(signUpRequestDto.getPhone());

        if(existingUser.isPresent()){
            throw new DuplicateEmailException();
        }

        User user = User.builder()
                .name(signUpRequestDto.getName())
                .nickname(signUpRequestDto.getName())
                .phone(signUpRequestDto.getPhone())
                .email(signUpRequestDto.getEmail())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .university(signUpRequestDto.getUniversity())
                .department(signUpRequestDto.getDepartment())
                .userType(signUpRequestDto.getUserType())
                .isProfileCompleted(true)
                .build();

        userRepository.save(user);

    }


}
