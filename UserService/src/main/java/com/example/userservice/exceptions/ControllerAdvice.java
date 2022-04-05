package com.example.userservice.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ExceptionHandler(value = {UserNotFoundException.class, RoleNotFoundException.class})
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex) {
        log.error(ex.getMessage());
        ApiErrorMessage error = new ApiErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                LocalDate.now(),
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InvalidOperationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleInvalidOperationException(InvalidOperationException ex) {
        log.error(ex.getMessage());
        ApiErrorMessage error = new ApiErrorMessage(
                HttpStatus.BAD_GATEWAY.value(),
                LocalDate.now(),
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);    }

}
