package com.javainuse.response;

import com.javainuse.dto.ErrorData;
import com.javainuse.dto.ExceptionType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BaseResponse {
    boolean status;
    private ExceptionType exceptionType;
    private List<String> validations;
    private ErrorData error;

}
