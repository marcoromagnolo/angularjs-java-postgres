package dmt.server.service.exception;

import dmt.server.enums.ErrorType;

import java.util.Map;

/**
 * @author Marco Romagnolo
 */
public class GenericException extends AbstractServiceException {

    public GenericException(ErrorType errorType) {
        super(errorType);
    }

    public GenericException(ErrorType errorType, Map<String, ErrorType> fields) {
        super(errorType, fields);
    }

}
