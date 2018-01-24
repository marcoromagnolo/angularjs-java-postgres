package dmt.server.data.helper;

/**
 * @author Marco Romagnolo
 */
public class JoinData {
    private final String table;
    private final String column;
    private final String joinTable;
    private final String joinColumn;
    private Object value;

    public JoinData(String table, String column, String joinTable, String joinColumn) {
        this.table = table;
        this.column = column;
        this.joinTable = joinTable;
        this.joinColumn = joinColumn;
    }

    public String getTable() {
        return table;
    }

    public String getColumn() {
        return column;
    }

    public String getJoinTable() {
        return joinTable;
    }

    public String getJoinColumn() {
        return joinColumn;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JoinData joinData = (JoinData) o;

        return !(joinTable != null ? !joinTable.equals(joinData.joinTable) : joinData.joinTable != null);

    }

    @Override
    public int hashCode() {
        return joinTable != null ? joinTable.hashCode() : 0;
    }
}
