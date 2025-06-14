package org.ms.library.catalog.controller.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.ms.library.catalog.dto.errors.ResourceNotFoundMessage;
import org.ms.library.catalog.service.exceptions.NoBookFoundException;
import org.ms.library.catalog.service.exceptions.NoCategoryFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler (NoBookFoundException.class)
    public ResponseEntity<ResourceNotFoundMessage>  handleNoBookFoundException(NoBookFoundException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ResourceNotFoundMessage message = new ResourceNotFoundMessage(ex.getMessage(), request.getRequestURI(), status.value(), Instant.now());
        return ResponseEntity.status(status).body(message);

    }

    @ExceptionHandler (NoCategoryFoundException.class)
    public ResponseEntity<ResourceNotFoundMessage>  handleNoBookFoundException(NoCategoryFoundException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ResourceNotFoundMessage message = new ResourceNotFoundMessage(ex.getMessage(), request.getRequestURI(), status.value(), Instant.now());
        return ResponseEntity.status(status).body(message);

    }

}
