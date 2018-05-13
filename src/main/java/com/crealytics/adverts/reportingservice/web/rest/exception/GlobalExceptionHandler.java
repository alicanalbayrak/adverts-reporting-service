package com.crealytics.adverts.reportingservice.web.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * @author alican.albayrak
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CommonExceptionResponse handleException(MissingServletRequestParameterException ex) {
        return new CommonExceptionResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CommonExceptionResponse handleException(MissingServletRequestPartException ex) {
        return new CommonExceptionResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CommonExceptionResponse handleException(MethodArgumentNotValidException ex) {

        String message = ex.getMessage();

        if (ex.getBindingResult() != null
                && ex.getBindingResult().getFieldErrors() != null
                && !ex.getBindingResult().getFieldErrors().isEmpty()) {

            // show only the first one
            FieldError fieldError = ex.getBindingResult().getFieldErrors().get(0);
            String field = fieldError.getField();
            String defaultMessage = fieldError.getDefaultMessage();
            message = field + " -> " + defaultMessage;
        }
        return new CommonExceptionResponse(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CommonExceptionResponse handleException(MethodArgumentTypeMismatchException ex) {
        String message = ex.getName() + " should be of type " + ex.getRequiredType().getName() + ". Please refer to api documentation for allowed values.";
        return new CommonExceptionResponse(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CommonExceptionResponse handleException(HttpMessageNotReadableException ex) {
        return new CommonExceptionResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public CommonExceptionResponse handleException(MethodArgumentConversionNotSupportedException ex) {
        String message = ex.getName() + " should be of type " + ex.getRequiredType().getName() + ". Please refer to api documentation for allowed values.";
        return new CommonExceptionResponse(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public CommonExceptionResponse handleException(HttpRequestMethodNotSupportedException ex) {
        return new CommonExceptionResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CommonExceptionResponse handleException(CommonException ex) {
        return new CommonExceptionResponse(ex.getHttpStatus(), ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public CommonExceptionResponse handleException(Exception ex) {
        return new CommonExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Sorry, something went wrong. Please try again later");
    }

}
