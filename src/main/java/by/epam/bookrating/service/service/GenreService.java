package by.epam.bookrating.service.service;

import by.epam.bookrating.dao.dao.GenreDAO;
import by.epam.bookrating.dao.dao.impl.MySQLBookDAO;
import by.epam.bookrating.dao.dao.impl.MySQLGenreDAO;
import by.epam.bookrating.dao.entity.Genre;
import by.epam.bookrating.dao.exception.DAOException;
import by.epam.bookrating.service.exception.ServiceException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by anyab on 29.11.2016.
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
    private static final Logger logger = Logger.getLogger(GenreService.class);

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

    public void deleteAllGenresForBook(long bookId) throws ServiceException{
        try{
            genreDao.deleteAllGenresForBook(bookId);
        } catch (DAOException e){
            throw new ServiceException("Exception in deleteAllGenresForBook, Service layer",e);
        }
    }
}
