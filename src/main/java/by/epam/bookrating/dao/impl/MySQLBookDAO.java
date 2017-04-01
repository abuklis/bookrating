package by.epam.bookrating.dao.impl;
import by.epam.bookrating.dao.BookDAO;
import by.epam.bookrating.pool.impl.ConnectionPool;
import by.epam.bookrating.entity.Book;
import by.epam.bookrating.pool.ConnectionPoolException;
import by.epam.bookrating.dao.DAOException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anyab on 11.09.16.
 */

public class MySQLBookDAO extends CloseStatement implements BookDAO {
    private MySQLBookDAO() {}
    private static MySQLBookDAO instance = new MySQLBookDAO();
    public static MySQLBookDAO getInstance()  {
        return instance;
    }
    private static Logger logger = Logger.getLogger(MySQLBookDAO.class);

    private static final String SQL_FIND_AMOUNT_OF_BOOKS =
            "SELECT count(*) from books";

    private static final String SQL_DELETE_BOOK_FOR_AUTHOR =
            "DELETE FROM authors_books WHERE author_id=? AND book_id=?";

    private static final String SQL_DELETE_BOOK_FROM_FAVORITE =
            "DELETE FROM favorite_books WHERE user_id=? AND book_id=?";

    private static final String SQL_DELETE_BOOK_FROM_READ =
            "DELETE FROM read_books WHERE user_id=? AND book_id=?";

    private static final String SQL_FIND_ALL_BOOKS =
            "SELECT book_id, title, description, publishing_year, image_url FROM books LIMIT ?, ?";

    private static final String SQL_ADD_BASIC_BOOK_INFO =
            "INSERT INTO books(title, description, publishing_year, image_url) values (?,?,?,?)";

    private static final String SQL_FIND_BOOK_BY_ID = "SELECT book_id, title, description, publishing_year, " +
            " image_url FROM books WHERE book_id = ?";

    private static final String SQL_FIND_POPULAR_BOOKS =
            "SELECT rating.book_id, (select avg(r2.rating) from rating as `r2`" +
                    "where rating.book_id=r2.book_id) as avgRating " +
                    "FROM rating GROUP by rating.book_id " +
                    "ORDER by avgRating DESC limit 3";
    //// TODO: 25.02.2017 что тут с запросом?

    private static final String SQL_FIND_BOOKS_BY_AUTHOR_ID =
            "SELECT books.book_id, books.title, books.description, books.publishing_year, " +
                    "books.image_url FROM books JOIN AUTHORS_BOOKS ON books.book_id=AUTHORS_BOOKS.book_id " +
                    "WHERE AUTHORS_BOOKS.AUTHOR_ID =?";

    private static final String SQL_FIND_BOOKS_BY_GENRE_ID =
            "SELECT BOOKS.BOOK_ID, BOOKS.title, BOOKS.description, BOOKS.PUBLISHING_YEAR, " +
                    "BOOKS.image_url FROM BOOKS JOIN GENRES_BOOKS ON BOOKS.BOOK_ID=GENRES_BOOKS.BOOK_ID " +
                    "WHERE GENRES_BOOKS.GENRE_ID =?";

    private static final String SQL_DELETE_BOOK_BY_ID = "DELETE FROM BOOKS WHERE BOOK_ID = ?";

    private static final String SQL_UPDATE_BOOK_ITEM =
            "UPDATE BOOKS SET TITLE=? , DESCRIPTION=? , PUBLISHING_YEAR=?, IMAGE_URL =? WHERE BOOK_ID=?";

    private static final String SQL_UPDATE_BOOK_AUTHOR =
            "UPDATE BOOKS SET TITLE=? , DESCRIPTION=? , PUBLISHING_YEAR=?, GENRE=?, IMAGE_URL =? WHERE BOOK_ID=?";

    private static final String SQL_IS_BOOK_BY_THIS_AUTHOR_EXISTS =
            "SELECT BOOK_ID, AUTHOR_ID FROM AUTHORS_BOOKS WHERE AUTHOR_ID = ? AND BOOK_ID=?";

    private static final String SQL_FIND_LIST_OF_FAVORITE_BOOKS =
            "SELECT BOOKS.BOOK_ID, BOOKS.TITLE, BOOKS.DESCRIPTION, BOOKS.PUBLISHING_YEAR, " +
            "BOOKS.IMAGE_URL FROM BOOKS JOIN FAVORITE_BOOKS ON BOOKS.BOOK_ID=FAVORITE_BOOKS.BOOK_ID " +
            "WHERE FAVORITE_BOOKS.user_id = ? ";

    private static final String SQL_FIND_LIST_OF_READ_BOOKS =
            "SELECT BOOKS.BOOK_ID, BOOKS.TITLE, BOOKS.DESCRIPTION, BOOKS.PUBLISHING_YEAR, " +
                    "BOOKS.IMAGE_URL FROM BOOKS JOIN READ_BOOKS ON BOOKS.BOOK_ID=READ_BOOKS.BOOK_ID " +
                    "WHERE READ_BOOKS.user_id = ? ";

    private static final String SQL_ADD_BOOK_TO_FAVORITE =
            "INSERT INTO favorite_books(user_id, book_id) VALUES (?,?)";

    private static final String SQL_ADD_BOOK_TO_READ =
            "INSERT INTO read_books(user_id, book_id) VALUES (?,?)";

    private static final String SQL_IS_BOOK_IS_IN_READ =
            "SELECT user_id, book_id FROM read_books WHERE user_id=? AND book_id=?";

    private static final String SQL_IS_BOOK_IS_IN_FAVORITE =
            "SELECT user_id, book_id FROM favorite_books WHERE user_id=? AND book_id=?";

    private static final String SQL_ADD_GENRE_TO_THE_BOOK =
            "INSERT INTO GENRES_BOOKS(GENRE_ID, BOOK_ID) VALUES (?,?)";

    private static final String SQL_ADD_AUTHOR_TO_BOOK =
            "INSERT INTO AUTHORS_BOOKS(AUTHOR_ID, BOOK_ID) VALUES (?,?)";

    public List<Book> findAllBooks(int from, int to) throws DAOException {
        List<Book> books = new ArrayList<>();
        Book book;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            String sqlQuery = "SELECT book_id, title, description, publishing_year, image_url" +
                    " FROM books LIMIT "+from + ","+ to;
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                book = new Book(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getInt(4),resultSet.getString(5));
                book.setAuthors(MySQLAuthorDAO.getInstance().findAuthorsByBookId(resultSet.getInt(1)));
                book.setGenres(MySQLGenreDAO.getInstance().findGenresByBookId(resultSet.getInt(1)));
                books.add(book);
            }

        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception in method findAllBooks", e);
        } finally {
            closeStatement(statement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return books;
    }

    @Override
    public int getAmountOfBooks() throws DAOException {
        Connection connection;
        Statement statement;
        int amountOfBooks = 0;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_FIND_AMOUNT_OF_BOOKS);
            if (resultSet.next()){
                amountOfBooks = resultSet.getInt(1);
                logger.info("Amount of books = " + amountOfBooks);
            }
        } catch (ConnectionPoolException | SQLException e){
            throw new DAOException("Exception occurred in", e);
        }
        return amountOfBooks;
    }

    public long addBasicBookInfo(String title, String description, int publishingYear, String imageUrl)
            throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_ADD_BASIC_BOOK_INFO, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3,publishingYear);
            preparedStatement.setString(4, imageUrl);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception is occurred during adding book.", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    public Book findBookById(long bookId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Book book = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_BOOK_BY_ID);
            preparedStatement.setLong(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                book = new Book(resultSet.getLong(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4),
                        resultSet.getString(5));
                book.setAuthors(MySQLAuthorDAO.getInstance().findAuthorsByBookId(bookId));
                book.setGenres(MySQLGenreDAO.getInstance().findGenresByBookId(bookId));
                logger.info("Book with id = " + bookId+ "founded.");
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception is occurred during finding book.", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return book;
    }

    public void deleteBookById(long bookId) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_BOOK_BY_ID);
            preparedStatement.setLong(1, bookId);
            preparedStatement.executeUpdate();
            logger.info("Book with id " + bookId + " successfully deleted from books.");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception is occurred during deleting book.", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    public void updateBookItem(String title, String description, int publishingYear, String imageUrl, long bookId)
            throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_BOOK_ITEM);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, publishingYear);
            preparedStatement.setString(4, imageUrl);
            preparedStatement.setLong(5, bookId);
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception is occurred during updating book.", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    public Map<Book, Double> findBooksWithHighRating() throws DAOException {
        Connection connection = null;
        Statement statement = null;
        Map<Book,Double> booksWithAvgRatings = new LinkedHashMap<>();
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_FIND_POPULAR_BOOKS);
            while (resultSet.next()){
                long bookId = resultSet.getLong(1);
                double avgRating = resultSet.getDouble(2);
                booksWithAvgRatings.put(findBookById(bookId), avgRating);
            }
        } catch (ConnectionPoolException | SQLException| DAOException e){
            throw new DAOException("Exception is occurred in addGenreToBook method.", e);
        } finally {
            closeStatement(statement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return booksWithAvgRatings;
    }

    public List<Book> findBooksByGenre(String genre, long genreId) throws DAOException {
        return findBooksByParameter(genre, genreId, SQL_FIND_BOOKS_BY_GENRE_ID);
    }

    public List<Book> findBooksByAuthor(String author, long authorId) throws DAOException {
        return findBooksByParameter(author, authorId, SQL_FIND_BOOKS_BY_AUTHOR_ID);
    }

    public boolean addToFavorite(long userId, long bookId) throws DAOException {
        return addBookTo(userId, bookId,SQL_ADD_BOOK_TO_FAVORITE);
    }

    public boolean addToRead(long userId, long bookId) throws DAOException {
        return addBookTo(userId, bookId, SQL_ADD_BOOK_TO_READ);
    }

    public boolean isBookInFavorite(long userId, long bookId) throws DAOException{
        return isBookHasAlreadyAdded(userId, bookId, SQL_IS_BOOK_IS_IN_FAVORITE);
    }

    public boolean isBookInRead(long userId, long bookId) throws DAOException{
        return isBookHasAlreadyAdded(userId, bookId, SQL_IS_BOOK_IS_IN_READ);
    }

    public List<Book> findReadBooks(long userId) throws DAOException {
        return findBooksByParameter("read", userId, SQL_FIND_LIST_OF_READ_BOOKS);
    }

    public List<Book> findFavoriteBooks(long userId) throws DAOException {
        return findBooksByParameter("favorite", userId, SQL_FIND_LIST_OF_FAVORITE_BOOKS);
    }

    public void addAuthorToBook(long authorId, long bookId) throws DAOException{
        addGenreOrAuthorToBook(authorId, bookId, SQL_ADD_AUTHOR_TO_BOOK);
    }

    public void addGenreToBook(long genreId, long bookId) throws DAOException{
        addGenreOrAuthorToBook(genreId, bookId, SQL_ADD_GENRE_TO_THE_BOOK);
    }

    public void deleteBookFromRead(long userId, long bookId) throws DAOException {
        try {
            deleteBookFrom(userId, bookId, SQL_DELETE_BOOK_FROM_READ);
        } catch (DAOException e) {
            throw new DAOException("Exception in method deleteBookFromRead", e);
        }
    }

    public void deleteBookFromFavorite(long userId, long bookId) throws DAOException {
        try {
            deleteBookFrom(userId, bookId, SQL_DELETE_BOOK_FROM_FAVORITE);
        } catch (DAOException e) {
            throw new DAOException("Exception in method deleteBookFromFavorite", e);
        }
    }

    public void deleteBookForAuthor(long authorId, long bookId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_BOOK_FOR_AUTHOR);
            preparedStatement.setLong(1, authorId);
            preparedStatement.setLong(2, bookId);
            preparedStatement.executeUpdate();
            logger.info("Book with id " + bookId + " deleted from author "+ authorId);
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception is occurred during deleting book.", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    public boolean isBookByThisAuthorAlreadyExists(long authorId, long bookId) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_IS_BOOK_BY_THIS_AUTHOR_EXISTS);
            preparedStatement.setLong(1, authorId);
            preparedStatement.setLong(2, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception is occurred during deleting book.", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    private void addGenreOrAuthorToBook(long genreOrAuthorId, long bookId, String query) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, genreOrAuthorId);
            preparedStatement.setLong(2, bookId);
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception is occurred in addGenreToBook method.", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    private List<Book> findBooksByParameter(String parameter, Object parameterValue,
                                                 String query) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Book> books = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, parameterValue);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Book book = new Book(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getInt(4),resultSet.getString(5));
                book.setAuthors(MySQLAuthorDAO.getInstance().findAuthorsByBookId(resultSet.getInt(1)));
                book.setGenres(MySQLGenreDAO.getInstance().findGenresByBookId(resultSet.getInt(1)));
                books.add(book);
            }
            logger.info(books.size() + parameter + " books found for this user.");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception is occurred during finding books by parameter" + parameter, e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return books;
    }

    private void deleteBookFrom(long userId, long bookId, String query) throws DAOException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, bookId);
            preparedStatement.executeUpdate();
            logger.info("Book with id = " + bookId + " deleted from favorite/read by user "+ userId);
        } catch (ConnectionPoolException | SQLException e){
            throw new DAOException("Exception in deleteFrom method.", e);
        }
        finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    private boolean addBookTo(long userId, long bookId,
                              String addingQuery) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(addingQuery);
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, bookId);
            preparedStatement.executeUpdate();
            return true;
        } catch (ConnectionPoolException | SQLException e){
            throw new DAOException("Exception is occurred during adding to favorite in DAO layer.", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    private boolean isBookHasAlreadyAdded(long userId, long bookId, String checkQuery) throws DAOException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(checkQuery);
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException | ConnectionPoolException e){
            throw new DAOException("Exception is occurred during adding to favorite in DAO layer.", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

}
