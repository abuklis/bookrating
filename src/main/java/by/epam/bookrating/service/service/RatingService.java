package by.epam.bookrating.service.service;
import by.epam.bookrating.dao.dao.RatingDAO;
import by.epam.bookrating.dao.dao.impl.MySQLRatingDAO;
import by.epam.bookrating.dao.entity.Rating;
import by.epam.bookrating.dao.exception.DAOException;
import by.epam.bookrating.service.exception.ServiceException;
import org.apache.log4j.Logger;


/**
 * Created by anyab on 23.10.16.
 */
public class RatingService {
    private RatingDAO ratingDao;
    private RatingService(){
        ratingDao = MySQLRatingDAO.getInstance();
    }
    private static RatingService instance = new RatingService();
    public static RatingService getInstance() {
        return instance;
    }
    private static final Logger logger = Logger.getLogger(RatingService.class);

    public void leaveRating(long userId, long bookId, int rating) throws ServiceException {
        try {
            ratingDao.addRating(userId, bookId, rating);
        } catch (DAOException e) {
            throw new ServiceException("Exception in RatingService.", e);
        }
    }

    private boolean isRatingExists(long bookId, long userId) throws ServiceException {
        try {
            return ratingDao.isRatingByThisUserExists(userId, bookId);
        } catch (DAOException e) {
            throw new ServiceException("Exception in RatingService.", e);
        }
    }

    private Rating findRatingByLoginAndBookId(long bookId, long userId) throws ServiceException {
        try {
            return ratingDao.findRatingByLoginAndBookId(bookId, userId);
        } catch (DAOException e) {
            throw new ServiceException("Exception in RatingService.", e);
        }
    }

    public int checkRating(long bookId, long userId) throws ServiceException {
        int rating = 0;
        if(isRatingExists(bookId, userId)){
            rating = findRatingByLoginAndBookId(bookId, userId).getRating();
        }
        logger.info("There is " + rating + " rating left by "+ userId + " for this book.");
        return rating;
    }

    public double findAvgRatingByBookId(long bookId) throws ServiceException {
        try {
            return ratingDao.findAvgRatingByBookId(bookId);
        } catch (DAOException e) {
            throw new ServiceException("Exception in RatingService.", e);
        }
    }

}
