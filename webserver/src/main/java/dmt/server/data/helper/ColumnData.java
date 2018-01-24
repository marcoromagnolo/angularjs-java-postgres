package dmt.server.data.helper;

import java.lang.reflect.Field;

/**
 * @author Marco Romagnolo
 */
public class ColumnData {

    private final boolean id;
    private final boolean version;
    private final String column;
    private final Field field;
    private final String table;
    private Object value;

    public ColumnData(boolean id, boolean version, String column, String table, Field field) {
        this.id = id;
        this.version = version;
        this.column = column;
        this.table = table;
        this.field = field;
    }

    public boolean isId() {
        return id;
    }

    public boolean isVersion() {
        return version;
    }

    public String getColumn() {
        return column;
    }

    public String getTable() {
        return table;
    }

    public Field getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
