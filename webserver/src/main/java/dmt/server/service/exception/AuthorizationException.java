package dmt.server.service.exception;

import dmt.server.enums.ErrorType;

/**
 * @author Marco Romagnolo
 */
public class AuthorizationException extends AbstractServiceException {

    public AuthorizationException(ErrorType errorType) {
        super(errorType);
    }
}
