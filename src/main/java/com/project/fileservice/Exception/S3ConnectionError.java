package com.project.fileservice.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class S3ConnectionError extends RuntimeException{
    public S3ConnectionError(String message) {
        super(message);
    }
}
