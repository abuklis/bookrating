package by.epam.bookrating.service;
import by.epam.bookrating.dao.RatingDAO;
import by.epam.bookrating.dao.impl.MySQLRatingDAO;
import by.epam.bookrating.entity.Rating;
import by.epam.bookrating.dao.DAOException;
import org.apache.log4j.Logger;

/**
 * <p> Class contains methods for for processing data
 * received from controller layer, concerning with ratings. </p>
 * @author anyab
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
    private static Logger logger = Logger.getLogger(RatingService.class);

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
