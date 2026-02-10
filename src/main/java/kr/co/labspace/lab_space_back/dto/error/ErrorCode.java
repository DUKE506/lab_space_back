package kr.co.labspace.lab_space_back.dto.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    //AUTH
    AUTH_PROVIDER_MISMATCH(HttpStatus.CONFLICT, "AUTHH001", "카카오 로그인으로 가입된 계정입니다. 카카오 로그인을 이용해주세요."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "AUTH002", "이미 존재하는 이메일입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "AUTH003", "잘못된 비밀번호입니다."),

    //USER
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER001", "사용자를 찾을 수 없습니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;
}
