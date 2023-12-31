package com.tb.eatclean.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ResponseDTO<T> {
    private T data;
    private String errorCode;
    private String message;
    private boolean success;

}
