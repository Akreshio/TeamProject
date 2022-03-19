package ru.intervale.TeamProject.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(value = {BookNotFoundException.class})
    public ResponseEntity<Object> handlerBookNotFoundException(Exception e) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        log.debug("Book not found", e);
        return new ResponseEntity<>(e.getMessage(), status);
    }


}
