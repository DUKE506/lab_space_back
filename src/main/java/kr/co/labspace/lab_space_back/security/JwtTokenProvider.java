package kr.co.labspace.lab_space_back.security;

import lombok.Value;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final long accessTokenExpiration;
    private  final long refreshTokenExpiration;

    public JwtTokenProvider(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys
    }
}
