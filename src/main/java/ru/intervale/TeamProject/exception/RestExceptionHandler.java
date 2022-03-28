package ru.intervale.TeamProject.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.DateTimeException;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler {

    private final String BAD_REQUEST_MESSAGE = "Input error, please check your input and try again";
    private final String SERVER_ERROR_MESSAGE = "Server unavailable, please try again later";

    @ExceptionHandler(value = {BookNotFoundException.class})
    public ResponseEntity<Object> handlerBookNotFoundException(BookNotFoundException ex) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        log.error("Book not found" + ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), status);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Object> handleDataAccessException(DataAccessException ex) {
        log.error("DataAccessException: " + ex.getMessage());
        return new ResponseEntity<>(BAD_REQUEST_MESSAGE,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        log.error("Exception: " + ex.getMessage());
        return new ResponseEntity<>(SERVER_ERROR_MESSAGE,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public ResponseEntity<Object> handleArrayDateTimeException(ArrayIndexOutOfBoundsException ex) {
        log.error("DateTimeException: " + ex.getMessage());
        return new ResponseEntity<>(BAD_REQUEST_MESSAGE,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<Object> handleDateTimeException(DateTimeException ex) {
        log.error("DateTimeException: " + ex.getMessage());
        return new ResponseEntity<>(BAD_REQUEST_MESSAGE,HttpStatus.BAD_REQUEST);
    }

}
