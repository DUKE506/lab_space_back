package kr.co.labspace.lab_space_back.global.exception;

import kr.co.labspace.lab_space_back.dto.error.ErrorCode;

public class InValidPasswordException extends BusinessException {
    public InValidPasswordException() {
        super(ErrorCode.INVALID_PASSWORD);
    }
}
