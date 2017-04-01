package by.epam.bookrating.service;
import by.epam.bookrating.dao.BookDAO;
import by.epam.bookrating.dao.GenreDAO;
import by.epam.bookrating.dao.impl.MySQLAuthorDAO;
import by.epam.bookrating.dao.impl.MySQLBookDAO;
import by.epam.bookrating.dao.impl.MySQLGenreDAO;
import by.epam.bookrating.entity.Book;
import by.epam.bookrating.dao.DAOException;
import by.epam.bookrating.service.util.ImageLogic;
import org.apache.log4j.Logger;

import javax.servlet.http.Part;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p> Class contains methods for for processing data
 * received from controller layer, concerning with books. </p>
 * @author anyab
 */
public class BookService {
    private static BookService instance = new BookService();
    public static BookService getInstance() {
        return instance;
    }
    private BookDAO bookDao;
    private GenreDAO genreDao;
    private BookService() {
        bookDao = MySQLBookDAO.getInstance();
        genreDao = MySQLGenreDAO.getInstance();
    }

    private static Logger logger = Logger.getLogger(BookService.class);
    private static final String AUTHOR = "author";
    private static final String TITLE = "title";
    private static final String AUTHORS_DELIMITER = ",";
    private static final String DESCRIPTION = "description";
    private static final String PUBLISHING_YEAR = "publishingYear";
    private static final String GENRE = "genre";
    private static final String OLD_IMAGE_URL = "oldImage";
    private static final String BOOK_ID = "bookId";
    private static final String BOOK_LIST = "books";
    private static final String IMG_DIRECTORY_PATH = "img/";


    /**
     * <p>Takes information about request, calls necessary method.</p>
     *
     * @param parameters is the list of parameters, taken from service lay.
     * @param imagePart is the image, taken from form in JSP
     * @param absoluteDiskPath is the absolute path for copying images
     */

    public void addBook(Map<String, String> parameters, Part imagePart, String absoluteDiskPath, String[] genres)
            throws ServiceException {
        try {
            String imageUrl = ImageLogic.addImage(imagePart, absoluteDiskPath);
            logger.info("Trying to add basic book info...");
            long bookId = bookDao.addBasicBookInfo(parameters.get(TITLE), parameters.get(DESCRIPTION),
                  Integer.parseInt(parameters.get(PUBLISHING_YEAR)), imageUrl);
            logger.info("Added. BookId = " + bookId);
            GenreService.getInstance().addGenresToTheBook(genres, bookId);
            List<String> authors = getListOfAuthorsFromString(parameters.get(AUTHOR));
            for (String author : authors){
                checkAuthorAndAddOrUpdate(author.trim(), bookId);
            }
           } catch (DAOException e) {
                throw new ServiceException("Service Exception is occurred in ths method.", e);
        }
    }

    public int getAmountOfBooks() throws ServiceException{
        try{
            return bookDao.getAmountOfBooks();
        } catch (DAOException e){
            throw new ServiceException("Eee", e);
        }
    }

    public void deleteBook(long bookId) throws ServiceException {
        try{
            bookDao.deleteBookById(bookId);
        } catch (DAOException e){
            throw new ServiceException("Service Exception is occurred in ths method.", e);
        }
    }

    public void editBook(Map<String, String> parameters, Part imagePart, String absoluteDiskPath, String[] genres)
            throws ServiceException {
        try {
            int bookId = Integer.parseInt(parameters.get(BOOK_ID));
            String fileName = ImageLogic.getFileName(imagePart);
            String realImageUrl = IMG_DIRECTORY_PATH + fileName;
            String imageUrl;
            if (!realImageUrl.equals(IMG_DIRECTORY_PATH)) {
                imageUrl = ImageLogic.addImage(imagePart, absoluteDiskPath);
            } else {
                imageUrl =  parameters.get(OLD_IMAGE_URL);
            }

            //Add genres to a book
           // GenreService.getInstance().deleteAllGenresForBook(bookId);
            GenreService.getInstance().addGenresToTheBook(genres, bookId);

            //Add authors to a book
            List<String> authorList = getListOfAuthorsFromString(parameters.get(AUTHOR));
           // AuthorService.getInstance().deleteAllAuthorsForBook(bookId);
            for (String author : authorList){
                checkAuthorAndAddOrUpdate(author.trim(), bookId);
            }

            logger.info("New image url " + imageUrl);
            logger.info("Trying to update news item with id = " + bookId);
            bookDao.updateBookItem(parameters.get(TITLE),
                    parameters.get(DESCRIPTION), Integer.parseInt(parameters.get(PUBLISHING_YEAR)),
                     imageUrl, bookId);
            logger.info("Basic book info updated");
            logger.info("Book is successfully updated.");
        } catch (DAOException e){
            throw new ServiceException("Service Exception is occurred in ths method.", e);
        }
    }

    public List<Book> findBooksByGenre(String parameter, int parameterValue) throws ServiceException {
        try {
            return bookDao.findBooksByGenre(parameter, parameterValue);
        } catch (DAOException e) {
            throw new ServiceException("Service Exception is occurred in ths method.", e);
        }
    }

    public List<Book> findBooksByAuthorId(String parameter, long authorId) throws ServiceException {
        try {
            return bookDao.findBooksByAuthor(parameter, authorId);
        } catch (DAOException e) {
            throw new ServiceException("Exception occurred during finding books by authorId");
        }
    }

    public List<Book> viewAllBooks(int from, int to) throws ServiceException {
        List<Book> booksArray;
        try{
            booksArray = bookDao.findAllBooks(from, to);
            logger.info("Sending " + booksArray.size() + "books array to a controller.");
        } catch (DAOException e){
            throw new ServiceException("Service Exception is occurred in this method.", e);
        }
        return booksArray;
    }

    public Map<Book, Double> findBooksWithHighRating() throws ServiceException {
        Map<Book, Double> booksWithRatings;
        try{
            booksWithRatings = bookDao.findBooksWithHighRating();
        } catch (DAOException e){
            throw new ServiceException("Service Exception is occurred in this method.", e);
        }
        return booksWithRatings;
    }

    public Book viewBookInfo(long bookId) throws ServiceException {
        Book currentBook;
        try{
            currentBook = bookDao.findBookById(bookId);
            currentBook.setAuthors(AuthorService.getInstance().findAuthorsByBookId(bookId));
            currentBook.setGenres(GenreService.getInstance().findGenresByBookId(bookId));
        } catch (DAOException e){
            throw new ServiceException("Service Exception is occurred in ths method.", e);
        }
        return currentBook;
    }

    private void checkAuthorAndAddOrUpdate(String author, long bookId) throws ServiceException {
        long authorId;
        try{
            logger.info("Check, if " + author + "exists.");
            if (AuthorService.getInstance().findAuthorIdByFullName(author)==0){
                logger.info("There is no such author in the author's base. Add him.");
                authorId = AuthorService.getInstance().addBasicAuthorInfo(author);
                logger.info("Added. AuthorId = " + authorId);
                addAuthorToBook(authorId, bookId);
            } else {
                logger.info("Author exists.");
                authorId = AuthorService.getInstance().findAuthorIdByFullName(author);
                addAuthorToBook(authorId, bookId);
            }
        } catch (ServiceException e){
            throw new ServiceException("Exception occurred during checking author.");
        }
    }

    private void addAuthorToBook(long authorId, long bookId) throws ServiceException {
        try{
            if (bookDao.isBookByThisAuthorAlreadyExists(authorId, bookId)){
                logger.info("Book with such name by this author already exists. Do nothing");
            } else {
                bookDao.addAuthorToBook(authorId, bookId);
                logger.info("Author with id = "+authorId + " added to a book "+ bookId);
            }
        } catch (DAOException e){
            throw new ServiceException("Exception occurred during adding author to a book.");
        }
    }

    private List<String> getListOfAuthorsFromString(String authors){
        return Arrays.asList(authors.split(AUTHORS_DELIMITER));
    }
}
