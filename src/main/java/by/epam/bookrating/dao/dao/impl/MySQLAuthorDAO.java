package by.epam.bookrating.dao.dao.impl;

import by.epam.bookrating.dao.connection.impl.ConnectionPool;
import by.epam.bookrating.dao.dao.AuthorDAO;
import by.epam.bookrating.dao.entity.Author;
import by.epam.bookrating.dao.exception.ConnectionPoolException;
import by.epam.bookrating.dao.exception.DAOException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by anyab on 12.11.16.
 */

public class MySQLAuthorDAO extends CloseStatement implements AuthorDAO {
    private static MySQLAuthorDAO instance = new MySQLAuthorDAO();
    public static MySQLAuthorDAO getInstance() {
        return instance;
    }
    private MySQLAuthorDAO() {
    }
    private static final Logger logger = Logger.getLogger(MySQLAuthorDAO.class);

    private static final String SQL_EDIT_AUTHOR_INFO =
            "UPDATE authors SET full_name=? , birth_year=? , birth_country=?, " +
                    "biography=?, image_url =? WHERE author_id=?";

    private static final String SQL_FIND_AUTHORS_FOR_BOOK = "SELECT authors.author_id, authors.full_name, " +
            "authors.birth_year, authors.birth_country, authors.biography, authors.image_url " +
            "FROM authors JOIN authors_books ON authors_books.author_id = authors.author_id " +
            "WHERE authors_books.book_id = ?";

    private static final String SQL_ADD_AUTHOR =
            "INSERT INTO authors(full_name, birth_year, birth_country, biography, image_url) VALUES (?,?,?,?, ?)";

    private static final String SQL_FIND_AUTHOR_BY_ID = "SELECT author_id, full_name, birth_year, birth_country, " +
            "biography, image_url FROM authors WHERE author_id = ?";

    private static final String SQL_FIND_AUTHOR_BY_FULLNAME = "SELECT author_id, full_name, birth_year, birth_country, " +
            "biography, image_url FROM authors WHERE full_name LIKE ?";

    private static final String SQL_ADD_BASIC_AUTHOR_INFO = "INSERT INTO authors(full_name) VALUES (?)";

    private static final String SQL_DELETE_ALL_AUTHORS_FOR_BOOK =
            "DELETE FROM authors_books WHERE book_id=?";


    public void addAuthor(String fullName, int birthYear, String birthCountry, String biography, String imageUrl)
            throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_ADD_AUTHOR);
            preparedStatement.setString(1, fullName);
            preparedStatement.setInt(2, birthYear);
            preparedStatement.setString(3, birthCountry);
            preparedStatement.setString(4, biography);
            preparedStatement.setString(5, imageUrl);
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception occurred during adding author.", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    public long addBasicAuthorInfo(String fullName) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_ADD_BASIC_AUTHOR_INFO, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, fullName);
            preparedStatement.executeUpdate();
            logger.info("Author with name " + fullName+" added to a database.");
            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception occurred during adding author.", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    public Author findAuthorById(long authorId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Author author = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_AUTHOR_BY_ID);
            preparedStatement.setLong(1, authorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                author = new Author(resultSet.getLong(1), resultSet.getString(2),
                        resultSet.getInt(3), resultSet.getString(4),
                        resultSet.getString(5), resultSet.getString(6));
                logger.info("Author with id = "+ authorId + " found.");
            } else {
                logger.info("Author with id = "+ authorId + "not found.");
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception is occurred during finding author by id.", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return author;
    }

    public Author isAuthorExists(String fullName) throws DAOException {
        ResultSet resultSet;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Author author = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_AUTHOR_BY_FULLNAME);
            preparedStatement.setString(1, fullName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                author = new Author(resultSet.getLong(1), resultSet.getString(2),
                        resultSet.getInt(3), resultSet.getString(4),
                        resultSet.getString(5), resultSet.getString(6));
                logger.info("Author with id = "+ author.getAuthorId()+ " founded.");
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception is occurred during finding author by name.", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return author;
    }

    public ArrayList<Author> findAuthorsByBookId(long bookId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ArrayList<Author> authors = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_AUTHORS_FOR_BOOK);
            preparedStatement.setLong(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                authors.add(new Author(resultSet.getLong(1), resultSet.getString(2), resultSet.getInt(3),
                        resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)));
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception occurred during findAuthorIdByBookId to a book.", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return authors;
    }

    public void updateAuthorInfo(String fullName, int birthYear, String birthCountry,
                                 String biography, String imageUrl, int authorId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_EDIT_AUTHOR_INFO);
            preparedStatement.setString(1, fullName);
            preparedStatement.setInt(2, birthYear);
            preparedStatement.setString(3, birthCountry);
            preparedStatement.setString(4, biography);
            preparedStatement.setString(5, imageUrl);
            preparedStatement.setInt(6, authorId);
            preparedStatement.executeUpdate();
            logger.info("Author updated.");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception occurred during updating author.", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    public void deleteAllAuthorsForBook(long bookId) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_ALL_AUTHORS_FOR_BOOK);
            preparedStatement.setLong(1, bookId);
            preparedStatement.executeUpdate();
            logger.info("Authors for the book "+ bookId + " deleted");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception occurred during deleting genres for book.", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }
}
