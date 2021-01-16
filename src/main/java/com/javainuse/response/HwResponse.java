package com.javainuse.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HwResponse {
    private String message;
    private Integer messageCode;
}
