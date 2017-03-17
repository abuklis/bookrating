package by.epam.bookrating.dao.connection.impl;

import by.epam.bookrating.dao.connection.IConnectionPool;
import by.epam.bookrating.dao.exception.ConnectionPoolException;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class {@code ConnectionPool} is the class that implements IConnectionPool interface
 * to deal with connections.
 * @author Anna Buklis
 */
public class ConnectionPool implements IConnectionPool {

    private BlockingQueue<Connection> availableConnectionsQueue;
    private BlockingQueue<Connection> usedConnectionsQueue;

    private String url;
    private String user;
    private String password;
    private String locationOfDriver;
    private int connectionAmount;
    private static final String KEY_BUNDLE = "db";
    private static final String KEY_URL = "DB_URL";
    private static final String KEY_USER = "DB_USERNAME";
    private static final String KEY_PASSWORD = "DB_PASSWORD";
    private static final String KEY_LOCATION_OF_DRIVER = "DB_DRIVER_CLASS";
    private static final String KEY_CONNECTION_AMOUNT="DB_CONNECTION_AMOUNT";
    private static final int DEFAULT_AMOUNT = 5;
    private static Lock lock = new ReentrantLock();

    private static ConnectionPool instance;
    private static AtomicBoolean instanceCreated = new AtomicBoolean(false);
    private final static Logger logger = Logger.getLogger(ConnectionPool.class);

    public static ConnectionPool getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (instanceCreated!=null) {
                    instance = new ConnectionPool();
                    instanceCreated.getAndSet(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    private ConnectionPool() {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(KEY_BUNDLE);
            this.url = bundle.getString(KEY_URL);
            this.user = bundle.getString(KEY_USER);
            this.password = bundle.getString(KEY_PASSWORD);
            this.locationOfDriver = bundle.getString(KEY_LOCATION_OF_DRIVER);
            this.connectionAmount = Integer.parseInt(bundle.getString(KEY_CONNECTION_AMOUNT));
            this.availableConnectionsQueue = new ArrayBlockingQueue<>(connectionAmount);
            this.usedConnectionsQueue = new ArrayBlockingQueue<>(connectionAmount);
            init();
        }  catch (MissingResourceException e) {
            logger.fatal("Resource bundle is not found. ", e);
            throw new RuntimeException("Resource bundle is not found.");
        }   catch(NumberFormatException e){
                this.connectionAmount = DEFAULT_AMOUNT;
        } catch (ConnectionPoolException e) {
            logger.fatal( "Wrong init in connection pool ", e);
            throw new RuntimeException("JDBC: Wrong init of connection pool class.", e);
        }
    }

    @PostConstruct
    private void init() throws ConnectionPoolException {
        logger.info("Trying to create connection pool");
        try {
            Class.forName(locationOfDriver);
            for(int i=0; i<connectionAmount; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                availableConnectionsQueue.put(connection);
                logger.info("Connection "+ i +" is created and put into the queue.");
            }
        } catch (ClassNotFoundException e) {
            logger.fatal("JDBC driver was not found", e);
            throw new RuntimeException("JDBC: driver is not founded", e);
        } catch (SQLException | InterruptedException e) {
            throw new ConnectionPoolException("SQL or InterruptedException is occurred in init method in ConnectionPool class", e);
        }
    }

    @Override
    public Connection takeConnection() throws ConnectionPoolException {
        Connection connection = null;
        try {
            connection = availableConnectionsQueue.take();
            usedConnectionsQueue.put(connection);
        } catch (InterruptedException e) {
            logger.error("Time is out. Can not take connection from the pool.", e);
        }
        logger.info("Connection is taken.");
        return connection;
    }

    @Override
    public void returnConnection(Connection connection){
        try {
            availableConnectionsQueue.put(connection);
            usedConnectionsQueue.remove(connection);
            logger.info("Connection is successfully returned.");
        } catch (InterruptedException e) {
            logger.error("Time is out. Can not put connection into the pool.", e);
        }

    }

    @Override
    public void destroyConnectionPool() throws ConnectionPoolException {
        try{
            for(int i=0; i<connectionAmount; i++) {
                Connection connection = ConnectionPool.getInstance().takeConnection();
                connection.close();
                logger.info("Connection "+ i +" is destroyed.");
            }
        } catch (SQLException e){
            throw new ConnectionPoolException("Exception occurred while destroying connection pool.", e);
        }

    }
}
