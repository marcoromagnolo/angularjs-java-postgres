package dmt.server.data.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Marco Romagnolo
 */
public class ConditionPool {

    private List<Condition> conditions = new ArrayList<>();
    private ConditionPool pool;
    private LogicType logic = LogicType.AND;

    public ConditionPool(ConditionPool pool) {
        this.pool = pool;
    }

    public ConditionPool(Condition condition) {
        Collections.addAll(this.conditions, condition);
    }

    public LogicType getLogic() {
        return logic;
    }

    public ConditionPool or(Condition condition) {
        condition.setLogic(LogicType.OR);
        Collections.addAll(this.conditions, condition);
        return this;
    }

    public ConditionPool and(Condition condition) {
        condition.setLogic(LogicType.AND);
        Collections.addAll(this.conditions, condition);
        return this;
    }

    public ConditionPool and(ConditionPool pool) {
        this.pool = pool;
        return this;
    }

    public ConditionPool or(ConditionPool pool) {
        this.pool = pool;
        return this;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public ConditionPool getPool() {
        return pool;
    }

}
