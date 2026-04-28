package com.zoopick.server.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.zoopick.server.dto.CommonResponse;
import com.zoopick.server.exception.ZoopickException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * 예외처리 핸들러
 */
@Slf4j
@RestControllerAdvice
public class ZoopickExceptionHandler {
    /**
     * 서버의 오류로 발생한 예외 처리
     *
     * @param exception 발생한 예외
     * @return 상태 코트 500의 {@link CommonResponse#error(String)}
     */
    @ExceptionHandler(value = {FirebaseMessagingException.class, IOException.class, MessagingException.class})
    public <T> ResponseEntity<CommonResponse<T>> handleInternalServerException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonResponse.error("Internal Server Error"));
    }

    /**
     * 클라이언트 요청에 대한 예외 처리
     *
     * @param exception 발생한 예외
     * @param request   Http 요청 정보
     * @return {@link ZoopickException#createResponseEntity()}로 만들어진 상태 코드와 응답
     * @see ZoopickException
     */
    @ExceptionHandler(ZoopickException.class)
    public <T> ResponseEntity<CommonResponse<T>> handleZoopickException(ZoopickException exception, HttpServletRequest request) {
        log.info("({}) {}", request.getRemoteAddr(), exception.getClientMessage());
        log.debug("Detail: ", exception);
        return exception.createResponseEntity();
    }
}
