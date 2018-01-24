package dmt.server.data.helper;

/**
 * @author Marco Romagnolo
 */
public enum OperatorType {
    eq("="), lt("<"), gt(">"), le("<="), ge(">=");


    private final String s;

    OperatorType(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return s;
    }
}
