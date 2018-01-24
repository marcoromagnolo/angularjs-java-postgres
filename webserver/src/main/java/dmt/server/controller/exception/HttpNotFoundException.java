package dmt.server.controller.exception;

import dmt.server.enums.ErrorType;
import org.springframework.http.HttpStatus;

/**
 * @author Marco Romagnolo
 */
public class HttpNotFoundException extends HttpException {

    public HttpNotFoundException() {
        super(HttpStatus.NOT_FOUND, ErrorType.ERROR_GENERIC);
    }

}
