package by.epam.bookrating.dao.dao.impl;
import by.epam.bookrating.dao.connection.impl.ConnectionPool;
import by.epam.bookrating.dao.dao.GenreDAO;
import by.epam.bookrating.dao.entity.Genre;
import by.epam.bookrating.dao.exception.ConnectionPoolException;
import by.epam.bookrating.dao.exception.DAOException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anyab on 29.11.2016.
 */
public class MySQLGenreDAO extends CloseStatement implements GenreDAO {
    private static MySQLGenreDAO instance = new MySQLGenreDAO();
    public static MySQLGenreDAO getInstance() {
        return instance;
    }
    private MySQLGenreDAO() {
    }
    private static final Logger logger = Logger.getLogger(MySQLGenreDAO.class);

    private final static String SQL_FIND_ALL_GENRES =  "SELECT GENRE_ID, GENRE_NAME FROM GENRES";

    private final static String SQL_FIND_GENRE_BY_ID =
            "SELECT GENRE_ID, GENRE_NAME FROM GENRES WHERE GENRE_ID = ?";

    private static final String SQL_GENRES_FOR_BOOK = "SELECT GENRES.GENRE_ID, GENRES.GENRE_NAME " +
            "FROM GENRES JOIN GENRES_BOOKS ON GENRES_BOOKS.GENRE_ID = GENRES.GENRE_ID " +
            "WHERE GENRES_BOOKS.BOOK_ID = ?";

    private static final String SQL_DELETE_ALL_GENRES_FOR_BOOK =
            " DELETE FROM GENRES_BOOKS WHERE BOOK_ID=?";

    public List<Genre> findAllGenres() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ArrayList<Genre> genres = new ArrayList<>();
        logger.info("Trying to get all genres from database...");
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_ALL_GENRES);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                genres.add(new Genre(resultSet.getInt(1), resultSet.getString(2)));
            }
        } catch (ConnectionPoolException | SQLException e){
            throw new DAOException("Exception in method findAllGenres, DAO layer",e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
        logger.info(genres.size() + " genres had founded.");
        return genres;
    }

    public Genre findGenreById(int genreId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Genre genre = new Genre();
        logger.info("Trying to get find genre in database...");
        try{
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_GENRE_BY_ID);
            preparedStatement.setInt(1, genreId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                genre.setGenreId(resultSet.getInt(1));
                genre.setGenreName(resultSet.getString(2));
            }
        } catch (ConnectionPoolException | SQLException e){
            throw new DAOException("Exception in method findAllGenres, DAO layer",e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return genre;
    }

    public List<Genre> findGenresByBookId(long bookId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ArrayList<Genre> genres = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_GENRES_FOR_BOOK);
            preparedStatement.setLong(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                genres.add(new Genre(resultSet.getInt(1), resultSet.getString(2)));
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception occurred during finding genres to a book.", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return genres;
    }

//    public boolean ifGenreAlreadyAdded(int genreId, int bookId){
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        try {
//            connection = ConnectionPool.getInstance().takeConnection();
//            preparedStatement = connection.prepareStatement(SQL_IF_GENRE_ALREADY_ADDED);
//            preparedStatement.setInt(1, bookId);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()){
//                genres.add(new Genre(resultSet.getInt(1), resultSet.getString(2)));
//            }
//            logger.info(genres.size() + " founded for this book.");
//        } catch (SQLException | ConnectionPoolException e) {
//            throw new DAOException("Exception occurred during findAuthorIdByBookId to a book.", e);
//        } finally {
//            closeStatement(preparedStatement);
//            ConnectionPool.getInstance().returnConnection(connection);
//        }
//        return genres;
//    }

    public void deleteAllGenresForBook(long bookId) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_ALL_GENRES_FOR_BOOK);
            preparedStatement.setLong(1, bookId);
            preparedStatement.executeUpdate();
            logger.info("Genres for the book "+ bookId + " deleted");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("Exception occurred during deleting genres for book.", e);
        } finally {
            closeStatement(preparedStatement);
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }


}
