package kr.co.labspace.lab_space_back.global.exception;

import kr.co.labspace.lab_space_back.dto.error.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }


}
