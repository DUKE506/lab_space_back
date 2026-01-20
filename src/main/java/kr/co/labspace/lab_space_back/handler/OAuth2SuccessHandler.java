package kr.co.labspace.lab_space_back.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.labspace.lab_space_back.entity.AuthProvider;
import kr.co.labspace.lab_space_back.entity.User;
import kr.co.labspace.lab_space_back.entity.UserType;
import kr.co.labspace.lab_space_back.repository.UserRepository;
import kr.co.labspace.lab_space_back.service.AuthCodeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Map;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final AuthCodeService authCodeService;

    public OAuth2SuccessHandler(UserRepository userRepository, AuthCodeService authCodeService){
        this.userRepository = userRepository;
        this.authCodeService = authCodeService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException{

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        Map<String, Object> attributes = oAuth2User.getAttributes();
        String kakaoId = String.valueOf(attributes.get("id"));

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        String email = (String) kakaoAccount.get("email");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        String nickname = (String) profile.get("nickname");



        //DB에서 사용자 조회 또는 생성
        User user = userRepository.findByProviderAndProviderId(AuthProvider.KAKAO, kakaoId)
                .orElseGet(()->{
                    // DB에 없으면 자동으로 새 사용자 생성
                    User newUser = User.builder()
                            .provider(AuthProvider.KAKAO)
                            .userType(UserType.GUEST)
                            .providerId(kakaoId)
                            .nickname(nickname)
                            .email(email)
                            .isProfileCompleted(false)
                            .build();
                    return userRepository.save(newUser);  // DB에 저장
                });

        // 일회용 인증 코드 생성  (Redis에 5분간 저장하면될듯)
        String authCode = authCodeService.generateCode(user.getId());

        //성공 페이지로 리다이렉트
        String targetUrl = UriComponentsBuilder
                .fromUriString("http://localhost:3333/auth/loginSuccess")
                .queryParam("code",authCode)
                .build()
                .toUriString();

        getRedirectStrategy().sendRedirect(request,response,targetUrl);
    }

    private String generateToken(User user) {
        return "temp-token-" + user.getId() + "-" + System.currentTimeMillis();
    }
}
