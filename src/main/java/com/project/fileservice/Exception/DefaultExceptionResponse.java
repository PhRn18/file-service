package com.project.fileservice.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Date;


@AllArgsConstructor
@Getter
@Setter
public class DefaultExceptionResponse {
    private String message;
    private Date exception_time;
    private HttpStatus http_status_code;
    private String error;
}
