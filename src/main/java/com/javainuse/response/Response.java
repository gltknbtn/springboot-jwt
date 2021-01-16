package com.javainuse.response;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> extends BaseResponse{
    private T data;
}
