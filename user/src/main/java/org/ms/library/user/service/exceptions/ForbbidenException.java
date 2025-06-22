package org.ms.library.user.service.exceptions;

public class ForbbidenException extends Throwable {


    public ForbbidenException() {
    }

    public ForbbidenException(String message) {
        super(message);
    }

    public ForbbidenException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForbbidenException(Throwable cause) {
        super(cause);
    }

    public ForbbidenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
