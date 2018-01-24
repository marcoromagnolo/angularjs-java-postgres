package dmt.server.data.helper;

import dmt.server.data.exception.DataException;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Marco Romagnolo
 * Converter from JDBC ResultSet to Entity and from Entity to JDBC ResultSet
 * @param <T>
 */
public class EntityConverter<T extends Entity> {

    private static final Logger LOGGER = Logger.getLogger(EntityConverter.class.getName());
    private final Class<T> entityClass;
    private final EntityReflect<T> entityReflect;

    /**
     * Constructor
     * @param entityClass the Class of an entity
     * @throws DataException
     */
    public EntityConverter(Class<T> entityClass) throws DataException {
        this.entityClass = entityClass;
        this.entityReflect = EntityReflect.getInstance(entityClass);
    }

    /**
     * Convert from result set to an entity
     * @param resultSet JDBC result obtained from a query
     * @return the entity with all fields set ad all entity children recursively
     * @throws DataException
     */
    public T toEntity(ResultSet resultSet) throws DataException {
        try {
            T entity = null;
            if (resultSet.next()) {
                entity = entityClass.newInstance();
                setEntityColumns(resultSet, entityReflect.getColumns(), entity);
                setEntityJoins(resultSet, entity, entityReflect);
                while (resultSet.next()) {
                    setEntityJoins(resultSet, entity, entityReflect);
                }
            }
            return entity;
        } catch (SQLException  | InstantiationException | IllegalAccessException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new DataException(e);
        }
    }

    /**
     * Recursively method to fill all entity join fields
     * @param resultSet passed from toEntity()
     * @param currentEntity the current generic Entity
     * @param currentReflect the current EntityReflect
     * @param <E> recursive Entity of joins
     * @return recursive Entity
     * @throws DataException
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private <E extends Entity> E setEntityJoins(ResultSet resultSet, E currentEntity, EntityReflect<E> currentReflect) throws DataException, SQLException, IllegalAccessException, InstantiationException {
        if (currentReflect.getJoinEntities()!=null) {
            for (Map.Entry<Field, Class> joinEntry : currentReflect.getJoinEntities().entrySet()) {
                Field field = joinEntry.getKey();
                field.setAccessible(true);
                Class<E> joinClass = joinEntry.getValue();
                EntityReflect<E> joinReflect = EntityReflect.getInstance(joinClass);
                if (field.getType().equals(List.class)) {
                    List<E> list = (List<E>) field.get(currentEntity);
                    if (list == null) {
                        list = new ArrayList<>();
                        field.set(currentEntity, list);
                    }
                    E joinEntity = joinClass.newInstance();
                    joinEntity.setId(getIdValue(joinReflect, resultSet));
                    int index = list.indexOf(joinEntity);
                    if (index == -1) {
                        setEntityColumns(resultSet, joinReflect.getColumns(), joinEntity);
                        list.add(joinEntity);
                    } else {
                        joinEntity = list.get(index);
                    }
                    setEntityJoins(resultSet, joinEntity, joinReflect);
                } else if (field.getType().equals(Set.class)) {
                    Set<E> set = (Set<E>) field.get(currentEntity);
                    if (set == null) {
                        set = new HashSet<>();
                        field.set(currentEntity, set);
                    }
                    E joinEntity = null;
                    for (E e : set) {
                        if (e.getId().equals(getIdValue(joinReflect, resultSet))) {
                            joinEntity = e;
                            break;
                        }
                    }
                    if (joinEntity==null) {
                        joinEntity = joinClass.newInstance();
                        setEntityColumns(resultSet, joinReflect.getColumns(), joinEntity);
                        set.add(joinEntity);
                    }
                    setEntityJoins(resultSet, joinEntity, joinReflect);
                } else {
                    E joinEntity = (E) field.get(currentEntity);
                    if (joinEntity == null) {
                        joinEntity = joinClass.newInstance();
                        setEntityColumns(resultSet, joinReflect.getColumns(), joinEntity);
                    }
                    setEntityJoins(resultSet, joinEntity, joinReflect);
                    field.set(currentEntity, joinEntity);
                }
            }
        }
        return currentEntity;
    }

    /**
     * Set fields of a Table of an Entity from ResultSet and a Set of ColumnData
     * @param resultSet passed from toEntity()
     * @param columns Set of ColumnData
     * @param currentEntity entity to populate
     * @param <E> generic recursive entity
     * @throws SQLException
     * @throws IllegalAccessException
     */
    private <E> void setEntityColumns(ResultSet resultSet, Set<ColumnData> columns, E currentEntity) throws SQLException, IllegalAccessException {
        for (ColumnData columnData : columns) {
            Field field = columnData.getField();
            field.setAccessible(true);
            String columnName = getColumnName(columnData.getTable(), columnData.getColumn());
            Class<?> type = field.getType();
            Object tmpValue = resultSet.getObject(columnName);
            if (tmpValue instanceof Integer) {
                field.set(currentEntity, tmpValue);
            } else if (tmpValue instanceof Long) {
                field.set(currentEntity, tmpValue);
            } else if (tmpValue instanceof Boolean) {
                field.set(currentEntity, tmpValue);
            } else if (tmpValue instanceof Float) {
                field.set(currentEntity, tmpValue);
            } else if (tmpValue instanceof Double) {
                field.set(currentEntity, tmpValue);
            } else if (tmpValue instanceof String) {
                field.set(currentEntity, tmpValue);
            } else if (tmpValue instanceof Date) {
                field.set(currentEntity, tmpValue);
            } else if (tmpValue instanceof Time) {
                field.set(currentEntity, tmpValue);
            } else if (tmpValue instanceof URL) {
                field.set(currentEntity, tmpValue);
            } else if (tmpValue instanceof BigDecimal) {
                field.set(currentEntity, tmpValue);
            }
//            if (type == Integer.class) {
//                field.set(currentEntity, resultSet.getInt(columnName));
//            } else if (type == Long.class) {
//                field.set(currentEntity, resultSet.getLong(columnName));
//            } else if (type == boolean.class) {
//                field.set(currentEntity, resultSet.getBoolean(columnName));
//            } else if (type == Boolean.class) {
//                field.set(currentEntity, resultSet.getBoolean(columnName));
//            } else if (type == Float.class) {
//                field.set(currentEntity, resultSet.getFloat(columnName));
//            } else if (type == Double.class) {
//                field.set(currentEntity, resultSet.getDouble(columnName));
//            } else if (type == String.class) {
//                field.set(currentEntity, resultSet.getString(columnName));
//            } else if (type == Date.class) {
//                field.set(currentEntity, resultSet.getDate(columnName));
//            } else if (type == Time.class) {
//                field.set(currentEntity, resultSet.getTime(columnName));
//            } else if (type == URL.class) {
//                field.set(currentEntity, resultSet.getURL(columnName));
//            } else if (type == BigDecimal.class) {
//                field.set(currentEntity, resultSet.getBigDecimal(columnName));
//            }
        }
    }

    /**
     * Convert in a List of Entity
     * @param resultSet from JDBC query
     * @return List of Entity
     * @throws DataException
     */
    public List<T> toEntities(ResultSet resultSet) throws DataException {
        try {
            ArrayList<T> list = new ArrayList<>();
            while (resultSet.next()) {
                T tmpEntity = entityClass.newInstance();
                Long currentId = getIdValue(entityReflect, resultSet);
                tmpEntity.setId(currentId);
                int index = list.indexOf(tmpEntity);
                if (index == -1) {
                    T entity = entityClass.newInstance();
                    setEntityColumns(resultSet, entityReflect.getColumns(), entity);
                    setEntityJoins(resultSet, entity, entityReflect);
                    list.add(entity);
                } else {
                    T entity = list.get(index);
                    setEntityJoins(resultSet, entity, entityReflect);
                }
            }
            return list;
        } catch (SQLException  | InstantiationException | IllegalAccessException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new DataException(e);
        }
    }

    /**
     * Convert from Entity to a List of ColumnData
     * Used to construct INSERT, UPDATE and DELETE query
     * @param entity take all Table fields without Entity fields
     * @return List of ColumnData
     * @throws DataException
     */
    public List<ColumnData> fromEntity(T entity) throws DataException {
        ArrayList<ColumnData> list = new ArrayList<>();
        if (entity!=null) {
            for(ColumnData columnData : entityReflect.getColumns()){
                Field field = columnData.getField();
                try {
                    Object value = field.get(entity);
                    columnData.setValue(value);
                    list.add(columnData);
                } catch (IllegalAccessException e) {
                    throw new DataException(e);
                }
            }
        }
        return list;
    }

    /**
     * Return the name of table column
     * @param table String name of a table
     * @param column String name of a column
     * @return String composed with table_column
     */
    public String getColumnName(String table, String column) {
        return table + "_" + column;
    }

    /**
     * Get the value of id directly from ResultSet
     * @param reflect EntityReflect
     * @param resultSet come from toEntity()
     * @return Long id of row
     * @throws SQLException
     */
    public Long getIdValue(EntityReflect reflect, ResultSet resultSet) throws SQLException {
        Field idField = reflect.getIdField();
        String idColumn = getColumnName(reflect.getTable(), idField.getName());
        return resultSet.getLong(idColumn);
    }
}
