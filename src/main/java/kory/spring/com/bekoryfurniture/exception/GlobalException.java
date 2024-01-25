package kory.spring.com.bekoryfurniture.exception;

import kory.spring.com.bekoryfurniture.dto.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException extends RuntimeException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode(HttpStatus.NOT_FOUND.value());
        exceptionResponse.setErrorMessage(exception.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
