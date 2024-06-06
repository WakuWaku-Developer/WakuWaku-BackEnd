package dev.backend.wakuwaku.global.exception;

import dev.backend.wakuwaku.global.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(WakuWakuException.class)
    public ResponseEntity<Object> handleWakuWakuException(WakuWakuException e) {
        ExceptionStatus status = e.getStatus();

        return ResponseEntity
                .status(status.getHttpStatus())
                .body(new BaseResponse<>(status));
    }
}
