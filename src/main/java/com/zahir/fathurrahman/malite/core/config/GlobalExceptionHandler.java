package com.zahir.fathurrahman.malite.core.config;

import com.zahir.fathurrahman.malite.core.exception.BadRequestException;
import com.zahir.fathurrahman.malite.core.model.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> exception(Exception e) {
        log.info("",e);
        return ResponseEntity.ok(new BaseResponse(
                false,
                500,
                "INTERNAL SERVER ERROR")
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BaseResponse> exception(BadRequestException e) {
        StackTraceElement t = e.getStackTrace()[0];
        log.info("responsing bad request from {} => {} => line : {}",t.getClassName(),t.getMethodName(),t.getLineNumber());
        return ResponseEntity.ok(new BaseResponse(
                false,
                400,
                e.getMessage())
        );
    }
}
