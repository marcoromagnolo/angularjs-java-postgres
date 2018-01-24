package dmt.server.service.exception;

import dmt.server.enums.ErrorType;

/**
 * @author Marco Romagnolo
 */
public class DbException extends InternalException {

    public DbException() {
        super(ErrorType.ERROR_DATABASE);
    }

}
