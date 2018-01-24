package dmt.server.component;

import dmt.server.data.exception.DataException;
import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Marco Romagnolo
 */
@Component
public class Database {

    private static final Logger logger = Logger.getLogger(Database.class.getName());
    private Connection connection;
    private PGPoolingDataSource ds;

    @Value("${db.host}")
    private String host;
    @Value("${db.port}")
    private int port;
    @Value("${db.database}")
    private String database;
    @Value("${db.username}")
    private String username;
    @Value("${db.password}")
    private String password;
    @Value("${db.max.connetions}")
    private int maxConnections;

    @PostConstruct
    public void init() {
        try {
            Class.forName("org.postgresql.Driver");
            ds = new PGPoolingDataSource();
            ds.setDataSourceName("datasource_" + database);
            ds.setServerName(host);
            ds.setPortNumber(port);
            ds.setDatabaseName(database);
            ds.setUser(username);
            ds.setPassword(password);
            ds.setMaxConnections(maxConnections);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private void connect() throws DataException {
        try {
            connection = ds.getConnection();
        } catch (SQLException e) {
            throw new DataException(e);
        }
    }

    public void disconnect() throws DataException {
        if (connection!=null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DataException(e);
            }
        }
    }

    public Connection getConnection() throws DataException {
        if (connection==null) {
            connect();
        }
        try {
            if (connection.isClosed() || !connection.isValid(0)) {
                connect();
            }
        } catch (SQLException ignored) {
            connect();
        }
        return connection;
    }

    public void begin() throws DataException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DataException(e);
        }
    }

    public void commit() throws DataException {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new DataException(e);
        }
    }

    public void rollback() throws DataException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DataException(e);
        }
    }
}
