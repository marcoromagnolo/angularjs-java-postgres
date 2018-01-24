package dmt.server.data.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Marco Romagnolo
 */
public class Conditions {

    private List<ConditionPool> pools = new ArrayList<>();
    private List<Condition> list = new ArrayList<>();

    public Conditions(Condition condition) {
        pools.add(new ConditionPool(condition));
        list.add(condition);
    }

    public Conditions and(Condition condition) {
        ConditionPool pool = new ConditionPool(condition);
        pools.add(pool);
        list.add(condition);
        return this;
    }

    public Conditions or(Condition condition) {
        ConditionPool pool = new ConditionPool(condition);
        pools.add(pool);
        list.add(condition);
        return this;
    }

    public Conditions and(ConditionPool pool) {
        pools.add(pool);
        list.addAll(pool.getConditions());
        return this;
    }

    public Conditions or(ConditionPool pool) {
        pools.add(pool);
        list.addAll(pool.getConditions());
        return this;
    }

    public List<ConditionPool> getPools() {
        return pools;
    }

    public List<Condition> getList() {
        return list;
    }

    private void recursivePool(StringBuilder sb, boolean isFirst, ConditionPool pool) {
        if (pool.getPool()!=null) {
            recursivePool(sb, true, pool.getPool());
        } else {
            if (isFirst) {
                iterateConditions(sb, pool.getConditions());
            } else {
                sb.append(pool.getLogic()).append(" (");
                iterateConditions(sb, pool.getConditions());
                sb.append(") ");
            }
        }
    }

    private void iterateConditions(StringBuilder sb, List<Condition> conditions) {
        Iterator<Condition> iterator = conditions.iterator();
        boolean isFirst = true;
        while (iterator.hasNext()) {
            Condition condition = iterator.next();
            if (isFirst) {
                sb.append(condition);
            } else {
                sb.append(condition.getLogic()).append(" ").append(condition).append(" ");
            }
            isFirst = false;
        }
    }

    public String asSQL() {
        final StringBuilder sb = new StringBuilder();
        Iterator<ConditionPool> iterator = pools.iterator();
        boolean isFirst = true;
        while (iterator.hasNext()) {
            ConditionPool pool = iterator.next();
            recursivePool(sb, isFirst, pool);
            isFirst = false;
        }
        return sb.toString();
    }
}
