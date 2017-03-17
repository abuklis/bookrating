package by.epam.bookrating.dao.dao.impl;

import by.epam.bookrating.dao.connection.impl.ConnectionPool;
import by.epam.bookrating.dao.dao.UserDAO;
import by.epam.bookrating.dao.entity.User;
import by.epam.bookrating.dao.exception.ConnectionPoolException;
import by.epam.bookrating.dao.exception.DAOException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anyab on 22.09.16.
 */
public class MySQLUserDAO extends CloseStatement implements UserDAO {
    private static final Logger logger = Logger.getLogger(MySQLUserDAO.class);
    private static MySQLUserDAO instance = new MySQLUserDAO();
    private MySQLUserDAO(){}
    public static MySQLUserDAO getInstance(){
        return instance;
    }

    private static final String SQL_FIND_COMMENTS_BY_USERID =
            "SELECT comment_id FROM comments WHERE user_id = ?";

    private static final String SQL_ADD_USER =
            "INSERT INTO users (login, password, name) VALUES (?,?,?)";

    private static final String SQL_FIND_ALL_USERS =
            "SELECT user_id, login, name, age, role FROM users WHERE role!='admin'";

    private static final String SQL_IF_USER_EXISTS =
            "SELECT login FROM users WHERE login = ?";

    private static final String SQL_UPDATE_PROFILE =
            "UPDATE users SET name=? , age=? , info=? WHERE user_id = ?";

    private static final String SQL_UPDATE_AVATAR =
            "UPDATE users SET avatar = ? WHERE user_id =?";

    private static final String SQL_BAN_USER = "UPDATE users SET role = 'banned' WHERE user_id = ?";

    private static final String SQL_QUERY_DELETE_USER = "DELETE FROM users WHERE user_id = ?";

    private static final String SQL_FIND_USER_INFO_BY_USERID =
            "SELECT user_id, login , password, name, age, info, avatar, role FROM users WHERE user_id = ?";

    private static final String SQL_FIND_USER_INFO_BY_LOGIN =
            "SELECT user_id, login , password, name, age, info, avatar, role FROM users WHERE login = ?";

    public void addUser(String login, String password, String name) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_ADD_USER);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, name);
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception is occurred during adding user to a db.", e);
        }finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    public User findUserByUserId(long userId) throws DAOException{
        User user = new User();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement =  connection.prepareStatement(SQL_FIND_USER_INFO_BY_USERID);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                user.setUserId(resultSet.getLong(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setName(resultSet.getString(4));
                user.setAge(resultSet.getInt(5));
                user.setInfo(resultSet.getString(6));
                user.setAvatar(resultSet.getString(7));
                user.setRole(resultSet.getString(8));
                logger.info("User found "+ user);
            } else {
                logger.info("User not found.");
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception is occurred in method .", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return user;
    }

    public User findUserByLogin(String login) throws DAOException{
        User user = new User();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement =  connection.prepareStatement(SQL_FIND_USER_INFO_BY_LOGIN);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                user.setUserId(resultSet.getLong(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setName(resultSet.getString(4));
                user.setAge(resultSet.getInt(5));
                user.setInfo(resultSet.getString(6));
                user.setAvatar(resultSet.getString(7));
                user.setRole(resultSet.getString(8));
                logger.info("User founded "+ user);
            } else {
                logger.info("User not found.");
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception is occurred in method .", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return user;
    }

    public boolean isLoginExists(String login) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean ifUserExists = false;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement =  connection.prepareStatement(SQL_IF_USER_EXISTS);
            preparedStatement.setString(1, login);
            logger.info("Trying to find user with login " + login + ".");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                logger.info("User with such login exists.");
                ifUserExists = true;
            } else {
                logger.info("There is no user with such login. ");
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception is occurred in method isLoginExists in UserDAO.", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return ifUserExists;
    }

    public void updateProfileInformation(String name, int age, String info, long userId)
            throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_PROFILE);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, info);
            preparedStatement.setLong(4, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception is occurred during updating profile in dao layer.", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    public void updateAvatar(long userId, String imageUrl) throws DAOException {
        updateUserInfoElement(imageUrl, userId, SQL_UPDATE_AVATAR);
    }

    public void banUser(long userId) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_BAN_USER);
            preparedStatement.setLong(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception is occurred during updating user info element.", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    public List<User> findAllUsers() throws DAOException{
        List<User> users = new ArrayList<>();
        Statement statement = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_USERS);
            while (resultSet.next()) {
                User user = new User(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getInt(4), resultSet.getString(5));
                users.add(user);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception in method findAllBooks", e);
        } finally {
            closeStatement(statement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return users;
    }

//    public int findNumberOfCommentsByLogin(long userId) throws DAOException{
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        //HashMap<String, Integer> numberOfUserCommentsByLogin= new HashMap<>();
//        int numberOfComments = 0;
//        try {
//            connection = ConnectionPool.getInstance().takeConnection();
//            preparedStatement = connection.prepareStatement(SQL_FIND_COMMENTS_BY_USERID);
//            preparedStatement.setLong(1, userId);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()){
//                logger.info("Size of comments by user " + resultSet.getFetchSize());
//                numberOfComments = resultSet.getFetchSize();
//            }
//        } catch (SQLException | ConnectionPoolException e) {
//            throw new DAOException("Exception in method findNumberOfCommentsByLogin", e);
//        } finally {
//            closeStatement(preparedStatement);
//            ConnectionPool.getInstance().returnConnection(connection);
//        }
//        return numberOfComments;
//    }

    private void updateUserInfoElement(String value, long userId, String query) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, value);
            preparedStatement.setLong(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception is occurred during updating user info element.", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }
}
