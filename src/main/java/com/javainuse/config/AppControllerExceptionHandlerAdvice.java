package com.javainuse.config;

import com.javainuse.dto.ErrorData;
import exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//@ControllerAdvice(basePackageClasses = AcmeController.class)
@ControllerAdvice
public class AppControllerExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    ResponseEntity<Object> handleControllerException(HttpServletRequest request, Throwable ex) {
        ResponseEntity<Object> rsp;
        HttpStatus status = getStatus(request);
        if(ex instanceof ValidationException){
            List<String> validations = ((ValidationException) ex).getValidations();
            rsp = new ResponseEntity<>(validations, HttpStatus.BAD_REQUEST);
        }else{
            rsp = new ResponseEntity<>(ErrorData.builder().errorCode(100).errorDesc(ex.getMessage()).build(), status);
        }
        return rsp;
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}

