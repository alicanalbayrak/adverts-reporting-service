package com.crealytics.adverts.reportingservice.web.rest.exception;

import org.springframework.http.HttpStatus;

/**
 * @author alican.albayrak
 */
public class BadRequestException extends CommonException {
    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
