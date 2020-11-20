package com.emr.example.exceptions;

import lombok.Getter;

@Getter
public class InvalidRequestParameterException extends  RuntimeException {

    private String parameterName;

    public InvalidRequestParameterException(String parameterName, String message) {
        super(message);

        this.parameterName = parameterName;
    }
}
