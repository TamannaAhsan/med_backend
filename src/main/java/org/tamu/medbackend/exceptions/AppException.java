package org.tamu.medbackend.exceptions;

import lombok.Getter;
import org.tamu.medbackend.enums.ErrorCode;

@Getter
public class AppException extends RuntimeException{

    private final ErrorCode errorCode;

    public AppException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

}
