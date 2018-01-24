package dmt.server.controller.exception;

import dmt.server.enums.ErrorType;
import dmt.server.service.exception.GenericException;
import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * @author Marco Romagnolo
 */
public abstract class HttpException extends RuntimeException {

    private HttpStatus httpStatus;

    private ErrorType errorType;

    private Map<String,ErrorType> fields;

    public HttpException(HttpStatus httpStatus, ErrorType errorType) {
        this.httpStatus = httpStatus;
        this.errorType = errorType;
    }

    public HttpException(HttpStatus httpStatus, GenericException e) {
        this.httpStatus = httpStatus;
        this.errorType = e.getType();
        this.fields = e.getFields();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public Map<String, ErrorType> getFields() {
        return fields;
    }

    public String getMessage() {
        return httpStatus.value() + " " + httpStatus.getReasonPhrase();
    }

}
