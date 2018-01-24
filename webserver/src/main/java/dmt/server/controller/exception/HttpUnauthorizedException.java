package dmt.server.controller.exception;

import dmt.server.enums.ErrorType;
import org.springframework.http.HttpStatus;

/**
 * @author Marco Romagnolo
 */
public class HttpUnauthorizedException extends HttpException {

    public HttpUnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED, ErrorType.ERROR_GENERIC);
    }
}
