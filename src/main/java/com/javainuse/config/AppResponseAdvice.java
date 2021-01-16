package com.javainuse.config;

import com.javainuse.dto.ErrorData;
import com.javainuse.dto.ExceptionType;
import com.javainuse.response.Response;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class AppResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
        ServerHttpResponse response) {

        Response<Object> output = new Response<>();
        int status = ((ServletServerHttpResponse) response).getServletResponse().getStatus();
        if(HttpStatus.OK.value() == status){
            output.setStatus(true);
            output.setData(body);
        }else{
            output.setStatus(false);
            output.setError((ErrorData) body);
            output.setExceptionType(ExceptionType.ERROR);
        }
        return output;
    }
}