package org.afs.pakinglot.advice;

import org.afs.pakinglot.exception.NoAvailablePositionException;
import org.afs.pakinglot.exception.UnrecognizedTicketException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(NoAvailablePositionException.class)
    public ResponseEntity<String> handleNoAvailablePositionException(NoAvailablePositionException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(UnrecognizedTicketException.class)
    public ResponseEntity<String> handleUnrecognizedTicketException(UnrecognizedTicketException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}