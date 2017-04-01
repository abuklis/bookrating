package by.epam.bookrating.service;
import by.epam.bookrating.dao.CommentDAO;
import by.epam.bookrating.dao.impl.MySQLCommentDAO;
import by.epam.bookrating.entity.Comment;
import by.epam.bookrating.entity.User;
import by.epam.bookrating.dao.DAOException;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * <p> Class contains methods for for processing data
 * received from controller layer, concerning with comments. </p>
 * @author anyab
 */
public class CommentService {
    private CommentDAO commentDao;
    private CommentService() { commentDao = MySQLCommentDAO.getInstance();}
    private static CommentService instance = new CommentService();
    public static CommentService getInstance() {
        return instance;
    }
    private static Logger logger = Logger.getLogger(CommentService.class);
    private static final int COMMENTS_NOT_FOUND = 0;

    public void leaveComment(long bookId, long userId, Date date, String commentText)
            throws ServiceException {
        try {
            commentDao.createComment(bookId, userId, date, commentText);
        } catch (DAOException e) {
            throw new ServiceException("Exception in CommentService, findAllCommentsByBookId method", e);
        }
    }

    public void deleteComment(long commentId) throws ServiceException {
        try {
            commentDao.deleteComment(commentId);
        } catch (DAOException e) {
            throw new ServiceException("Exception in CommentService, findAllCommentsByBookId method", e);
        }
    }

    public Map<User, Comment> findCommentsByBookId(long bookId) throws ServiceException {
        try {
            return commentDao.findAllCommentsByBookId(bookId);
        } catch (DAOException e) {
            throw new ServiceException("Exception in CommentService, findAllCommentsByBookId method", e);
        }
    }

    public List<Comment> viewTodayComments(Date currentDate) throws ServiceException{
        try {
            List<Comment> comments = commentDao.findTodayComments(currentDate);
            if (comments.size()==COMMENTS_NOT_FOUND){
                logger.warn("No comments by such date.");
            } return comments;
        } catch (DAOException e) {
            throw new ServiceException("Exception in CommentService, viewTodayComments method", e);
        }
    }

    public List<Comment> findCommentsByUserId(long userId) throws ServiceException{
        logger.info("Finding comments by user " + userId);
        try {
            return commentDao.findCommentsByUserId(userId);
        } catch (DAOException e) {
            throw new ServiceException("Exception in this method", e);
        }
    }

}
