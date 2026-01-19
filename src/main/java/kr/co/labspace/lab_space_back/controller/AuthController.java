package kr.co.labspace.lab_space_back.controller;


import kr.co.labspace.lab_space_back.dto.LoginRequestDto;
import kr.co.labspace.lab_space_back.dto.LoginResponseDto;
import kr.co.labspace.lab_space_back.entity.User;
import kr.co.labspace.lab_space_back.service.AuthCodeService;
import kr.co.labspace.lab_space_back.service.AuthService;
import kr.co.labspace.lab_space_back.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {


    @Autowired
    private UserService userService;
    private AuthService authService;
    private AuthCodeService authCodeService;


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

    @PostMapping("/token")
    public ResponseEntity<?> exchangeToken(@RequestBody Map<String,String> request){
        String code = request.get("code");

        //코드가 없는 경우
        if(code == null || code.isEmpty()){
            return ResponseEntity.badRequest().body(Map.of("error","Code is required"));
        }

        //코드 검증및 사용자 ID 조회
        Long userId = authCodeService.validateAdnConsume(code);

        if(userId == null){
            return ResponseEntity.status(401).body(Map.of("error","Invalid or expired code"));
        }

        //사용자 조회
        User user = userService.getUserByUserId(userId);

        //토큰생성


    }



}
