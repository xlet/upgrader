package org.xlet.upgrader.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Creator: JimmyLin
 * DateTime: 14-10-10 下午4:59
 * Summary:
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such resource")
public class TargetNotFoundException  extends RuntimeException{

    public TargetNotFoundException() {
    }

    public TargetNotFoundException(String message) {
        super(message);
    }
}
