package com.javainuse.config;

import com.javainuse.dto.ErrorData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

//@ControllerAdvice(basePackageClasses = AcmeController.class)
@ControllerAdvice
public class AppControllerExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    ResponseEntity<ErrorData> handleControllerException(HttpServletRequest request, Throwable ex) {
        HttpStatus status = getStatus(request);
        return new ResponseEntity<>(ErrorData.builder().errorCode(100).errorDesc(ex.getMessage()).build(), status);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}

