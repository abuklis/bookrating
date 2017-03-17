package by.epam.bookrating.dao.dao.impl;
import by.epam.bookrating.dao.connection.impl.ConnectionPool;
import by.epam.bookrating.dao.dao.RatingDAO;
import by.epam.bookrating.dao.entity.Rating;
import by.epam.bookrating.dao.exception.ConnectionPoolException;
import by.epam.bookrating.dao.exception.DAOException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by anyab on 30.09.16.
 */

public class MySQLRatingDAO extends CloseStatement implements RatingDAO {
    private static final Logger logger = Logger.getLogger(MySQLRatingDAO.class);
    private static MySQLRatingDAO instance = new MySQLRatingDAO();
    public static MySQLRatingDAO getInstance()  {
        return instance;
    }
    private MySQLRatingDAO(){}

    private static final String SQL_LEAVE_RATING =
            "INSERT INTO rating(user_id, book_id, rating) VALUES (?,?,?)";

    private static final String SQL_FIND_AVG_STAR =
            "SELECT AVG(rating) FROM rating WHERE book_id=?";

    private static final String SQL_IS_USER_LEFT_RATING =
            "SELECT user_id, book_id, rating FROM rating WHERE user_id= ? AND book_id= ?";

    private static final String SQL_FIND_RATING_BY_USERID_AND_BOOKID =
            "SELECT user_id, book_id, RATING FROM RATING " +
                    "WHERE user_id= ? AND book_id= ?";

    public void addRating(long userId, long bookId, int rating) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_LEAVE_RATING);
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, bookId);
            preparedStatement.setInt(3, rating);
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception is occurred during leaving rating.", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    public boolean isRatingByThisUserExists(long userId, long bookId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_IS_USER_LEFT_RATING);
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, bookId);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception is occurred during checking existing of rating.", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    public Rating findRatingByLoginAndBookId(long bookId, long userId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        Rating rating = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_RATING_BY_USERID_AND_BOOKID);
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, bookId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                rating = new Rating(resultSet.getLong(1),
                                    resultSet.getLong(2),
                                    resultSet.getInt(3));
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception is occurred during leaving rating.", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return rating;
    }

    public double findAvgRatingByBookId(long bookId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        double avgRating = 0;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_AVG_STAR);
            preparedStatement.setLong(1, bookId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                avgRating = resultSet.getDouble(1);
            }
            logger.info("Average rating of the book with id " + bookId + " = " + avgRating);
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception is occurred during leaving rating.", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return avgRating;
    }

}
