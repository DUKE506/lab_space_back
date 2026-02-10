package kr.co.labspace.lab_space_back.global.exception;

import kr.co.labspace.lab_space_back.dto.error.ErrorCode;

public class UserNotFoundException extends BusinessException{

    public UserNotFoundException(){
        super(ErrorCode.USER_NOT_FOUND);
    }
}
