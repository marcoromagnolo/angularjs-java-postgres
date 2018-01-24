package dmt.server.data.exception;

/**
 * @author Marco Romagnolo
 */
public class TooManyResultsException extends DataException {

    public TooManyResultsException() {
        super("Error while find data: founded too many results");
    }

}
