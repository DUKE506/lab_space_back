//package kr.co.labspace.lab_space_back.util;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.JwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import jakarta.annotation.PostConstruct;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//
//@Component
//@Slf4j
//public class JwtUtil {
//
//    @Value("${jwt.secret}")
//    private String jwtSecretKey;
//
//    private SecretKey key;
//
//    @PostConstruct
//    public void init() {
//        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));
//    }
//
//    public String resolveAccessToken(HttpServletRequest request){
//        String bearerToken = request.getHeader("Authorization");
//        log.info("bearerToken Value : "+bearerToken);
//        return bearerToken;
//    }
//
//    public Claims validateToken(String token){
//        try{
//            return Jwts.parser()
//                    .verifyWith(key)
//                    .build()
//                    .parseSignedClaims(token)
//                    .getPayload();
//
//        }catch (JwtException | IllegalArgumentException e){
//            return null;
//        }
//    }
//}
