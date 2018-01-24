package dmt.server.data.repository.impl;

import dmt.server.component.Database;
import dmt.server.data.exception.DataException;
import dmt.server.data.exception.EntityParseException;
import dmt.server.data.helper.*;
import dmt.server.data.repository.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Marco Romagnolo
 * AbstractRepo
 * @param <T>
 */
@Component
public abstract class AbstractRepo<T extends Entity> implements Repo<T> {

	private static final Logger LOGGER = Logger.getLogger(AbstractRepo.class.getName());

	@Autowired
	private Database db;
	private PreparedStatement pstmt;
	private Statement stmt;
	private Class<T> entityClass;
	private EntityReflect<? extends Entity> reflect;

	@PostConstruct
	public void init() throws DataException {
		entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		reflect = EntityReflect.getInstance(entityClass);
	}

	protected Connection getConnection() throws DataException {
		return db.getConnection();
	}

	@Override
	public void close() {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}

    @Override
    public void clearTable() throws DataException {
        try {
            String sql = "TRUNCATE TABLE " + reflect.getTable();
            pstmt = getConnection().prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    protected ResultSet select() throws SQLException, DataException {
		return select(null);
	}

	protected ResultSet select(Conditions conditions) throws SQLException, DataException {
		return select(conditions, null);
	}

    private String where(Conditions conditions) throws SQLException {
        String where = "";
        if (conditions != null) {
            where = " WHERE " + conditions.asSQL() + " ";
        }
        return where;
    }

	protected ResultSet select(Conditions conditions, Integer limitVal) throws SQLException, DataException {
		String select = "SELECT";
		String from = "FROM " + reflect.getTable();
		String join = "";
        Set<ColumnData> columns = new HashSet<>();
        columns.addAll(reflect.getColumns());
        getTableColumnsForSelect(columns, reflect);
        List<JoinData> joins = new ArrayList<>();
        getJoinsForSelect(reflect, joins, new JoinData(null, null, reflect.getTable(), null));
        Iterator<ColumnData> iterator = columns.iterator();
        while (iterator.hasNext()) {
            ColumnData column = iterator.next();
            boolean isLastColumn = !iterator.hasNext() && !iterator.hasNext();
            select += " " + column.getTable() + "." + column.getColumn() + " AS " + column.getTable() + "_"
                    + column.getColumn() + (isLastColumn ? " " : ",");
        }
        for (JoinData joinData : joins) {
            join += " LEFT JOIN " + joinData.getJoinTable() + " AS " + joinData.getJoinTable()
                    + " ON " + joinData.getJoinTable() + "." + joinData.getJoinColumn()
                    + "=" + joinData.getTable() + "." + joinData.getColumn();
        }
        String where = where(conditions);
        String limit = limitVal!=null ? " LIMIT " + limitVal : "";
		String sql = select + from + join + where + limit;
		LOGGER.info(sql);
        pstmt = getConnection().prepareStatement(sql);
		setObjects(conditions);
		return pstmt.executeQuery();
	}

	@Override
	public T insert(T entity) throws DataException {
		try {
			List<ColumnData> columnsData = fromEntity(entity);
			String query = "INSERT INTO " + reflect.getTable();
			String columns = " (";
			String values = " (";
			Iterator<ColumnData> iterator = columnsData.iterator();
            boolean idIsNull = false;
            while(iterator.hasNext()){
                ColumnData columnData = iterator.next();
                String column = columnData.getColumn();
                Object value;
                if (column.equals("version")) {
                    value = "1";
                } else if (column.equals("id")) {
                    value = columnData.getValue();
                    if (value == null) {
                        idIsNull = true;
                        continue;
                    }
                } else {
                    value = Parser.parse(columnData.getValue());
                    value = value == null ? "NULL" : "'" + value + "'";
                }
                columns += column + ",";
                values += value + ",";
            }
            columns = columns.substring(0, columns.length()-1) + ")";
            values = values.substring(0, values.length()-1) + ")";
            query += columns + " VALUES " + values;
			stmt = getConnection().createStatement();
			stmt.execute(query, Statement.RETURN_GENERATED_KEYS);
			if (idIsNull) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    entity.setId(rs.getLong(1));
                }
                rs.close();
            }
			return entity;
		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			close();
		}
	}

    @Override
	public int update(T entity) throws DataException {
		Long id = null;
		try {
			List<ColumnData> columns = fromEntity(entity);
			String query = "UPDATE " + reflect.getTable() + " SET ";
			Iterator<ColumnData> iterator = columns.iterator();
			while(iterator.hasNext()){
				ColumnData columnData = iterator.next();
				if(columnData.getColumn().equals("id")){
					id = (Long) columnData.getValue();
				}
				if(!columnData.getColumn().equals("id") && columnData.getValue() != null){
					query += columnData.getColumn() + " = '" + columnData.getValue() + "',";
				}
			}
			query = query.substring(0, (query.length()-1));
			query += " WHERE id = " + id;
            pstmt = getConnection().prepareStatement(query);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			close();
		}
	}

	@Override
	public int update(T entity, Conditions conditions) throws DataException {
		Long id = null;
		try {
			List<ColumnData> columns = fromEntity(entity);
			String query = "UPDATE " + reflect.getTable() + " SET ";
			Iterator<ColumnData> iterator = columns.iterator();
			while(iterator.hasNext()){
				ColumnData columnData = iterator.next();
				if(columnData.getColumn().equals("id")){
					id = (Long) columnData.getValue();
				}
				if(!columnData.getColumn().equals("id")){
					query += columnData.getColumn() + " = " + (columnData.getValue() == null?columnData.getValue() +
							",":"'"+ columnData.getValue() + "',");
				}
			}
			query = query.substring(0, (query.length()-1));
			String where = where(conditions);
			query += where;
			pstmt = getConnection().prepareStatement(query);
			setObjects(conditions);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			close();
		}
	}

	@Override
	public T save(T entity) throws DataException {
		try {
			Conditions conditions = new Conditions(new Condition(entityClass, "id", ((Entity)entity).getId()));
			ResultSet resultSet = select(conditions);
			Long id = null;
			while (resultSet.next()) {
				EntityConverter<T> converter = new EntityConverter<>(entityClass);
				String columnName = converter.getColumnName(reflect.getTable(),"id");
				id = resultSet.getLong(columnName);
			}
			if (id==null) {
				insert(entity);
			} else {
				update(entity, new Conditions(new Condition(entityClass, "id", ((Entity)entity).getId())));
			}
			return entity;
		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			close();
		}
	}

	@Override
	public void delete(T entity) throws DataException {
		try {
            String sql = "DELETE FROM " + reflect.getTable() + " WHERE id=?";
            pstmt = getConnection().prepareStatement(sql);
			pstmt.setObject(1, (entity).getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			close();
		}
	}


	public void setObjects(Conditions conditions) throws SQLException {
		if (conditions != null) {
			int i = 0;
			for (Condition condition : conditions.getList()) {
				pstmt.setObject(++i, condition.getValue());
			}
		}
	}

    @Override
    public void delete(Conditions conditions) throws DataException {
        try {
            String sql = "DELETE FROM " + reflect.getTable() + where(conditions);
            pstmt = getConnection().prepareStatement(sql);
			setObjects(conditions);
            //pstmt.setObject(1, (entity).getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataException(e);
        } finally {
            close();
        }
    }

	@Override
	public List<T> findAll() throws DataException {
		try {
            ResultSet rs = select();
            return toEntities(rs);
		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			close();
		}
	}

    @Override
	public T find(Long id) throws DataException {
		try {
			Conditions conditions = new Conditions(new Condition(entityClass, "id", id));
			ResultSet rs = select(conditions);
            return toEntity(rs);
		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			close();
		}
	}

	private void getTableColumnsForSelect(Set<ColumnData> columns, EntityReflect<? extends Entity> entityReflect) throws EntityParseException {
        for (Class joinEntitiyClass : entityReflect.getJoinEntities().values()) {
            EntityReflect joinEntityReflect = EntityReflect.getInstance(joinEntitiyClass);
            columns.addAll(joinEntityReflect.getColumns());
            getTableColumnsForSelect(columns, joinEntityReflect);
        }
	}
	
	private void getJoinsForSelect(EntityReflect<? extends Entity> reflect, List<JoinData> joins, JoinData doNotJoin) throws EntityParseException {
        for (JoinData join : reflect.getJoins()) {
            if (!joins.contains(join) && !join.equals(doNotJoin)) {
                joins.add(join);
            }
        }
        for (Class joinEntityClass : reflect.getJoinEntities().values()) {
            EntityReflect joinEntityReflect = EntityReflect.getInstance(joinEntityClass);
            getJoinsForSelect(joinEntityReflect, joins, doNotJoin);
        }
	}

    protected T toEntity(ResultSet rs) throws DataException {
        EntityConverter<T> converter = new EntityConverter<>(entityClass);
        return converter.toEntity(rs);
    }

    protected List<T> toEntities(ResultSet rs) throws DataException {
        EntityConverter<T> converter = new EntityConverter<>(entityClass);
        try {
            return converter.toEntities(select());
        } catch (SQLException e) {
            throw new DataException(e);
        }
    }

    protected List<ColumnData> fromEntity(T entity) throws DataException {
        EntityConverter<T> converter = new EntityConverter<>(entityClass);
        return converter.fromEntity(entity);
    }

    protected EntityReflect<? extends Entity> getReflect() {
        return reflect;
    }

}
