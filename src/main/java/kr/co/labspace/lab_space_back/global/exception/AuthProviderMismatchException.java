package kr.co.labspace.lab_space_back.global.exception;

import kr.co.labspace.lab_space_back.dto.error.ErrorCode;

public class AuthProviderMismatchException extends BusinessException{

    public AuthProviderMismatchException(){
        super(ErrorCode.AUTH_PROVIDER_MISMATCH);
    }
}
