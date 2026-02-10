package kr.co.labspace.lab_space_back.handler;

import kr.co.labspace.lab_space_back.dto.error.ErrorCode;
import kr.co.labspace.lab_space_back.dto.error.ErrorResponse;
import kr.co.labspace.lab_space_back.global.exception.BusinessException;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handle(BusinessException e){
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ErrorResponse.of(e.getErrorCode()));
    }
}
