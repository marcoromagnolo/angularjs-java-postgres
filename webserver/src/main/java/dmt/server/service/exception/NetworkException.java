package dmt.server.service.exception;

import dmt.server.enums.ErrorType;

/**
 * @author Marco Romagnolo
 */
public class NetworkException extends InternalException {

    protected NetworkException() {
        super(ErrorType.ERROR_NETWORK);
    }
}
