package dmt.server.data.helper;

import dmt.server.data.exception.EntityParseException;

/**
 * @author Marco Romagnolo
 */
public class Condition {

    private String table;
    private String column;
    private Object value;
    private OperatorType op;
    private LogicType logic;

    public Condition(Class<? extends Entity> entityClass, String column, Object value) throws EntityParseException {
        this(entityClass, column, OperatorType.eq, value);
    }

    public Condition(Class<? extends Entity> entityClass, String column, OperatorType op, Object value) throws EntityParseException {
        this.value = value;
        this.op = op;
        EntityReflect<? extends Entity> reflect = EntityReflect.getInstance(entityClass);
        this.table = reflect.getTable();
        this.column = reflect.getColumnFromProperty(column);
    }

    public void setLogic(LogicType logic) {
        this.logic = logic;
    }

    public String getColumn() {
        return column;
    }

    public Object getValue() {
        return value;
    }

    public OperatorType getOp() {
        return op;
    }

    public LogicType getLogic() {
        return logic;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(table).append(".").append(column).append(op).append("?");
        return sb.toString();
    }
}
