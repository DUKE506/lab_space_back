package kr.co.labspace.lab_space_back.global.exception;

import kr.co.labspace.lab_space_back.dto.error.ErrorCode;

public class DuplicateEmailException extends BusinessException{

    public DuplicateEmailException(){
        super(ErrorCode.DUPLICATE_EMAIL);
    }
}
