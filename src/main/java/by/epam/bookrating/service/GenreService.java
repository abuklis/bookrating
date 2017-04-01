package by.epam.bookrating.service;

import by.epam.bookrating.dao.GenreDAO;
import by.epam.bookrating.dao.impl.MySQLBookDAO;
import by.epam.bookrating.dao.impl.MySQLGenreDAO;
import by.epam.bookrating.entity.Genre;
import by.epam.bookrating.dao.DAOException;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * <p> Class contains methods for for processing data
 * received from controller layer, concerning with genres. </p>
 * @author anyab
 */
public class GenreService {
    private GenreDAO genreDao;
    private static GenreService ourInstance = new GenreService();
    public static GenreService getInstance() {
        return ourInstance;
    }
    private GenreService() {
        genreDao = MySQLGenreDAO.getInstance();
    }
    private static Logger logger = Logger.getLogger(GenreService.class);

    public List<Genre> findAllGenres() throws ServiceException{
        try{
            return genreDao.findAllGenres();
        } catch (DAOException e){
            throw new ServiceException("Exception in findAllGenres in Service.", e);
        }
    }

    public Genre findGenreById(int genreId) throws ServiceException{
        try{
            return genreDao.findGenreById(genreId);
        } catch (DAOException e){
            throw new ServiceException("Exception .", e);
        }
    }

    public List<Genre> findGenresByBookId(long bookId) throws ServiceException {
        try{
            return genreDao.findGenresByBookId(bookId);
        } catch (DAOException e){
            throw new ServiceException("Exception in findGenresByBookId, Service layer",e);
        }
    }

    public void addGenresToTheBook(String[] genres, long bookId) throws ServiceException{
        try{
            for (String genre: genres){
                MySQLBookDAO.getInstance().addGenreToBook(Long.parseLong(genre), bookId);
            }
        } catch (DAOException e){
            throw new ServiceException("Exception in addGenreToTheBook, Service layer",e);
        }
    }

}
