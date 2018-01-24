package dmt.server.controller.exception;

import dmt.server.enums.ErrorType;
import org.springframework.http.HttpStatus;

/**
 * @author Marco Romagnolo
 */
public class HttpInternalServerErrorException extends HttpException {

    public HttpInternalServerErrorException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ErrorType.ERROR_GENERIC);
    }

}
