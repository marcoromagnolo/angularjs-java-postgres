package dmt.server.controller.exception;

import dmt.server.enums.ErrorType;
import org.springframework.http.HttpStatus;

/**
 * @author Marco Romagnolo
 */
public class HttpBadRequestException extends HttpException {

    public HttpBadRequestException(ErrorType errorType) {
        super(HttpStatus.FORBIDDEN, errorType);
    }

}
