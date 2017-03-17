package by.epam.bookrating.dao.connection;

import by.epam.bookrating.dao.exception.ConnectionPoolException;

import java.sql.Connection;

/**
 * Interface {@code by.bsu.bookrating.connection.IConnectionPool} has the only two methods to take connections and return them to the queue.
 * @author Anna Buklis
 */
public interface IConnectionPool {
    /**
     * <p>Takes needed connection in the connection pool.</p>
     * @return {@code Connection} to connect to the database.
     */
    Connection takeConnection() throws ConnectionPoolException;

    /**
     * <p>Returns connection to the pool.</p>
     * <p>
     * @param connection is the connection, that should be returned to the pool.
     */
    void returnConnection(Connection connection) throws ConnectionPoolException;
    void destroyConnectionPool() throws ConnectionPoolException;

}
