package com.crealytics.adverts.reportingservice.web.rest.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * @author alican.albayrak
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonExceptionResponse {

    private final long timestamp;

    @JsonIgnore
    private final HttpStatus httpStatus;

    private final int status;

    private final String error;

    private final String message;

    public CommonExceptionResponse(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.status = httpStatus != null ? httpStatus.value() : 0;
        this.error = httpStatus != null ? httpStatus.getReasonPhrase() : null;
        timestamp = new Date().getTime();
    }

}
