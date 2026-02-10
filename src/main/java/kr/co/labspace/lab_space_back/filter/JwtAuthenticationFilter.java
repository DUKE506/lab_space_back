package kr.co.labspace.lab_space_back.filter;


import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.labspace.lab_space_back.config.SecurityConfig;
import kr.co.labspace.lab_space_back.entity.User;
import kr.co.labspace.lab_space_back.repository.UserRepository;
import kr.co.labspace.lab_space_back.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter  extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    //생성자 주입
    public JwtAuthenticationFilter (
            JwtTokenProvider jwtTokenProvider,
            UserRepository userRepository
    ){
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    // JWT 검증을 건너뛸 경로들
    private static final String[] EXCLUDE_URLS = {
            "/",
            "/oauth2/**",
            "/login/**",
            "/api/auth/**",
//            "/api/users/**",
            "/api/labs/admin/**"
    };


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException{
        log.info("==========JWT AUTHENTICATION FILTER==========");
        String requestURI = request.getRequestURI();
        if(isExcludedPath(requestURI)){
            log.info("JWT 검증 제외 경로 : {}", requestURI);
            filterChain.doFilter(request,response);
            return;
        }

        String accessToken = jwtTokenProvider.resolveToken(request);
        Boolean isValid = jwtTokenProvider.validateToken(accessToken);
        Claims claims = jwtTokenProvider.parseClaims(accessToken);
        log.info("accessToken : " + accessToken);
        log.info("validate result : " + isValid);
        if(accessToken != null && isValid){
            //인증
            log.info(claims.toString());
            //사용자 정보 조회
            Long userId = Long.parseLong(claims.getSubject());
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not Found"));

            //컨트롤러에서 userId 사용할 수 있도록 세팅
//            request.setAttribute("userId", user.getId());

            //인증객체 생성
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    null
            );
            //Spring Security에게 인증 완료 알림
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("인증된 사용자 요청!");
        }else{
            //거부
            log.info("인증되지않은 사용자 요청!");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"message\": \"인증되지 않은 사용자입니다.\"}");
            return;
        }
        filterChain.doFilter(request,response);
    }

    // 경로 매칭 메서드
    private boolean isExcludedPath(String requestURI) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        for (String pattern : EXCLUDE_URLS) {
            if (pathMatcher.match(pattern, requestURI)) {
                return true;
            }
        }
        return false;
    }

}
