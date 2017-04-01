package by.epam.bookrating.dao.impl;
import by.epam.bookrating.dao.CommentDAO;
import by.epam.bookrating.dao.DAOException;
import by.epam.bookrating.pool.impl.ConnectionPool;
import by.epam.bookrating.entity.Comment;
import by.epam.bookrating.entity.User;
import by.epam.bookrating.pool.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anyab on 25.09.16.
 */
public class MySQLCommentDAO extends CloseStatement implements CommentDAO {
    private static Logger logger = Logger.getLogger(MySQLCommentDAO.class);
    private MySQLCommentDAO(){}
    private static MySQLCommentDAO instance = new MySQLCommentDAO();
    public static MySQLCommentDAO getInstance(){
        return instance;
    }

    private static final String SQL_ADD_COMMENT_TO_THE_BOOK =
            "INSERT INTO comments(book_id, user_id, comment_date, comment_text) VALUES (?,?,?,?)";

    private static final String SQL_FIND_ALL_COMMENTS_BY_BOOKID =
            "SELECT comments.comment_id, comments.book_id, comments.user_id, comments.comment_date, " +
                    "comments.comment_text FROM comments WHERE comments.book_id = ?";

    private static final String SQL_FIND_COMMENTS_BY_DATE =
            "SELECT  comment_id, book_id, user_id, comment_date, comment_text" +
                    " FROM comments WHERE COMMENT_DATE = ?";

    private static final String SQL_FIND_COMMENTS_BY_USER_ID =
            "SELECT comment_id, book_id, user_id, comment_date, comment_text " +
                    "FROM comments WHERE user_id = ? ";

    private static final String SQL_DELETE_COMMENT_BY_ID =
            "DELETE FROM comments WHERE comment_id = ?";

    public void createComment(long bookId, long userId, Date commentDate, String commentText)
            throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_ADD_COMMENT_TO_THE_BOOK);
            preparedStatement.setLong(1, bookId);
            preparedStatement.setLong(2, userId);
            preparedStatement.setDate(3, commentDate);
            preparedStatement.setString(4, commentText);
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception is occurred during adding book.", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    public Map<User, Comment> findAllCommentsByBookId(long bookId) throws DAOException {
        Map<User, Comment> commentsWithLogin = new HashMap<>();
        ResultSet resultSet;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_ALL_COMMENTS_BY_BOOKID);
            preparedStatement.setLong(1, bookId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet!=null){
                while (resultSet.next()) {
                    Comment comment = new Comment(resultSet.getLong(1),resultSet.getLong(2),
                            resultSet.getLong(3), resultSet.getDate(4), resultSet.getString(5));
                    User user = MySQLUserDAO.getInstance().findUserByUserId(resultSet.getLong(3));
                    commentsWithLogin.put(user, comment);
                }
                System.out.println("Size " + commentsWithLogin.size() );
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception in method findAllBooks", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return commentsWithLogin;
    }

    public void deleteComment(long commentId) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_COMMENT_BY_ID);
            preparedStatement.setLong(1, commentId);
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception is occurred during deleting comment.", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    public List<Comment> findTodayComments(Date currentDate) throws DAOException {
        List<Comment> comments = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_COMMENTS_BY_DATE);
            preparedStatement.setDate(1, currentDate);
            resultSet = preparedStatement.executeQuery();
            if (resultSet!=null){
                while (resultSet.next()) {
                    Comment comment = new Comment(resultSet.getLong(1),resultSet.getLong(2),
                            resultSet.getLong(3), resultSet.getDate(4), resultSet.getString(5));
                    comments.add(comment);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception in method findAllBooks", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return comments;
    }

    public List<Comment> findCommentsByUserId(long userId) throws DAOException {
        ArrayList<Comment> comments = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_COMMENTS_BY_USER_ID);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet!=null){
                while (resultSet.next()) {
                    Comment comment = new Comment(resultSet.getLong(1),resultSet.getLong(2),
                            resultSet.getLong(3), resultSet.getDate(4), resultSet.getString(5));
                    comments.add(comment);
                }
                logger.info(comments.size()+ " comments are found.");
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception in method findUserCommentsByLogin", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return comments;
    }
}
