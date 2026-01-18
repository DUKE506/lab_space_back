package kr.co.labspace.lab_space_back.controller;


import kr.co.labspace.lab_space_back.dto.LoginRequestDto;
import kr.co.labspace.lab_space_back.dto.LoginResponseDto;
import kr.co.labspace.lab_space_back.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginDto){
//        log.info("========== 로그인 요청 시작 ==========");
//        log.info("받은 데이터: {}", loginDto);
//        log.info("이메일: {}", loginDto.getEmail());
//        log.info("비밀번호: {}", loginDto.getPassword());
//        log.info("비밀번호 길이: {}", loginDto.getPassword().length());
//        log.info("====================================");
        try{
            LoginResponseDto responseDto = authService.login(loginDto);
            return ResponseEntity.ok(responseDto);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().build();
        }

    }
}
