package org.ms.library.catalog.service.exceptions;

public abstract class NoResouceFoundException extends RuntimeException {
    public NoResouceFoundException(String message) {
        super(message);
    }
}
