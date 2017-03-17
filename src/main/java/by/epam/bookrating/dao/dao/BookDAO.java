package by.epam.bookrating.dao.dao;
import by.epam.bookrating.dao.entity.Book;
import by.epam.bookrating.dao.exception.DAOException;
import java.util.List;
import java.util.Map;

/**
 * Created by anyab on 09.02.2017.
 */
public interface BookDAO {
    List<Book> findAllBooks(int from, int to) throws DAOException;

    int getAmountOfBooks() throws DAOException;

    long addBasicBookInfo(String title, String description, int publishingYear, String imageUrl) throws DAOException;

    Book findBookById(long bookId) throws DAOException;

    void deleteBookById(long bookId) throws DAOException;

    void updateBookItem(String title, String description, int publishingYear, String imageUrl, long bookId)
            throws DAOException;

    Map<Book, Double> findBooksWithHighRating() throws DAOException;

    List<Book> findBooksByGenre(String genre, long genreId) throws DAOException;

    List<Book> findBooksByAuthor(String author, long authorId) throws DAOException;

    boolean addToFavorite(long userId, long bookId) throws DAOException;

    boolean addToRead(long userId, long bookId) throws DAOException;

    boolean isBookInFavorite(long userId, long bookId) throws DAOException;

    boolean isBookInRead(long userId, long bookId) throws DAOException;

    List<Book> findReadBooks(long userId) throws DAOException;

    List<Book> findFavoriteBooks(long userId) throws DAOException;

    void deleteBookFromRead(long userId, long bookId) throws DAOException;

    void deleteBookFromFavorite(long userId, long bookId) throws DAOException;

    boolean isBookByThisAuthorAlreadyExists(long authorId, long bookId) throws DAOException;

    void addAuthorToBook(long authorId, long bookId) throws DAOException;
}
