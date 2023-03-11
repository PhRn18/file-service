package com.project.fileservice.Exception;

import org.joda.time.LocalDate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@org.springframework.web.bind.annotation.ControllerAdvice
@RestController
public class ControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { InvalidFileException.class })
    protected ResponseEntity<DefaultExceptionResponse> handleAddS3Error(Exception e,WebRequest request) {
        HttpStatus status = HttpStatus.BAD_GATEWAY;
        DefaultExceptionResponse defaultExceptionResponse = new DefaultExceptionResponse(
                e.getMessage(),new Date(),status,e.toString()
        );
        return new ResponseEntity<>(defaultExceptionResponse,status);
    }
    @ExceptionHandler(value={S3ConnectionError.class})
    protected ResponseEntity<DefaultExceptionResponse> handleS3ListError(Exception e,WebRequest request){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        DefaultExceptionResponse defaultExceptionResponse = new DefaultExceptionResponse(
                e.getMessage(),new Date(),status,e.toString()
        );
        return new ResponseEntity<>(defaultExceptionResponse,status);
    }

}
