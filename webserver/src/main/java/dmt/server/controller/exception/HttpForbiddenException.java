package dmt.server.controller.exception;

import dmt.server.service.exception.GenericException;
import org.springframework.http.HttpStatus;

/**
 * @author Marco Romagnolo
 */
public class HttpForbiddenException extends HttpException {

    public HttpForbiddenException(GenericException e) {
        super(HttpStatus.BAD_REQUEST, e);
    }

}
