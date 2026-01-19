package kr.co.labspace.lab_space_back.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OAuth2Controller {

    @GetMapping("/loginSuccess")
    public Map<String, Object> loginSuccess(@AuthenticationPrincipal OAuth2User oAuth2User){
        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> kakaoAccount = (Map<String,Object>) attributes.get("kakao_account");

        return Map.of(
                "scucess",true,
                "message","로그인 성공",
                "user", kakaoAccount
        );
    }
    @GetMapping("/loginFailure")
    public Map<String, String> loginFailure() {
        return Map.of(
                "success", "false",
                "message", "로그인 실패"
        );
    }

}
