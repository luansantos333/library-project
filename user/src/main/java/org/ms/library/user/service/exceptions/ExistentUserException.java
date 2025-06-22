package org.ms.library.user.service.exceptions;

public class ExistentUserException extends RuntimeException {
    public ExistentUserException(String message) {
        super(message);
    }

    public ExistentUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistentUserException(Throwable cause) {
        super(cause);
    }

    public ExistentUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ExistentUserException() {
    }
}
