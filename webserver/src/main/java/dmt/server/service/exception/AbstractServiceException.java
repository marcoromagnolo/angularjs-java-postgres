package dmt.server.service.exception;

import dmt.server.enums.ErrorType;

import java.util.Map;

/**
 * @author Marco Romagnolo
 */
public abstract class AbstractServiceException extends Exception {

    ErrorType type;

    private Map<String, ErrorType> fields;

    protected AbstractServiceException(ErrorType errorType) {
        super(errorType.getMessage());
        this.type = errorType;
    }

    protected AbstractServiceException(ErrorType errorType, Map<String, ErrorType> fields) {
        this(errorType);
        this.fields = fields;
    }

    public ErrorType getType() {
        return type;
    }

    public Map<String, ErrorType> getFields() {
        return fields;
    }

}
