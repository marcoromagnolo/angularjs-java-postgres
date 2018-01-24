package dmt.server.service.exception;

import dmt.server.enums.ErrorType;

/**
 * @author Marco Romagnolo
 */
public abstract class InternalException extends AbstractServiceException {

    protected InternalException(ErrorType errorType) {
        super(errorType);
    }
}
