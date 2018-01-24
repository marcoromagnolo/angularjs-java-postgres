package dmt.server.service.exception;

import dmt.server.enums.ErrorType;

/**
 * @author Marco Romagnolo
 */
public class AuthenticationException extends AbstractServiceException {

    public AuthenticationException(ErrorType errorType) {
        super(errorType);
    }
}
