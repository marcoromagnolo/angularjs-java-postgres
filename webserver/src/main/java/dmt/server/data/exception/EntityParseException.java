package dmt.server.data.exception;

/**
 * @author Marco Romagnolo
 */
public class EntityParseException extends DataException {
    public EntityParseException(String message) {
        super(message);
    }

    public EntityParseException(Exception e) {
        super(e);
    }
}
