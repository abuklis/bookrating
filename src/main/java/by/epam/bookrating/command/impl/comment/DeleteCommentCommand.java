package by.epam.bookrating.command.impl.comment;
import by.epam.bookrating.command.Command;
import by.epam.bookrating.command.PageConstant;
import by.epam.bookrating.command.ParameterConstant;
import by.epam.bookrating.command.CommandException;
import by.epam.bookrating.service.ServiceException;
import by.epam.bookrating.service.BookService;
import by.epam.bookrating.service.CommentService;
import by.epam.bookrating.service.RatingService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.epam.bookrating.command.ParameterConstant.*;

/**
 * Created by anyab on 27.10.16.
 */
public class DeleteCommentCommand implements Command {
    private DeleteCommentCommand(){}
    private static DeleteCommentCommand instance = new DeleteCommentCommand();
    public static DeleteCommentCommand getInstance() {
        return instance;
    }
    private static Logger logger = Logger.getLogger(DeleteCommentCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        long bookId = Long.parseLong(request.getParameter(BOOK_ID));
        long commentId = Long.parseLong(request.getParameter(COMMENT_ID));
        logger.info("Delete comment " + commentId + " by book "+ bookId);
        try{
            CommentService.getInstance().deleteComment(commentId);
            request.setAttribute(BOOK_ITEM, BookService.getInstance().viewBookInfo(bookId));
            request.setAttribute(AVG_RATING, RatingService.getInstance().findAvgRatingByBookId(bookId));
            request.setAttribute(COMMENTS, CommentService.getInstance().findCommentsByBookId(bookId));
        } catch (ServiceException e){
            throw new CommandException("Exception is occurred.", e);
        }
        return PageConstant.VIEW_SINGLE;
    }
}
