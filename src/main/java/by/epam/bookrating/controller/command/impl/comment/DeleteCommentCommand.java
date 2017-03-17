package by.epam.bookrating.controller.command.impl.comment;
import by.epam.bookrating.controller.command.Command;
import by.epam.bookrating.controller.constant.PageConstant;
import by.epam.bookrating.controller.constant.ParameterConstant;
import by.epam.bookrating.controller.exception.CommandException;
import by.epam.bookrating.service.exception.ServiceException;
import by.epam.bookrating.service.service.BookService;
import by.epam.bookrating.service.service.CommentService;
import by.epam.bookrating.service.service.RatingService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by anyab on 27.10.16.
 */
public class DeleteCommentCommand implements Command {
    private DeleteCommentCommand(){}
    private static DeleteCommentCommand instance = new DeleteCommentCommand();
    public static DeleteCommentCommand getInstance() {
        return instance;
    }
    private static final Logger logger = Logger.getLogger(DeleteCommentCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        long bookId = Long.parseLong(request.getParameter(ParameterConstant.BOOK_ID));
        int commentId = Integer.parseInt(request.getParameter(ParameterConstant.COMMENT_ID));
        logger.info("Delete comment " + commentId + " by book "+ bookId);
        try{
            CommentService.getInstance().deleteComment(commentId);
            request.setAttribute(ParameterConstant.BOOK_ITEM, BookService.getInstance().viewBookInfo(bookId));
            request.setAttribute(ParameterConstant.AVG_RATING, RatingService.getInstance().findAvgRatingByBookId(bookId));
            request.setAttribute(ParameterConstant.COMMENTS, CommentService.getInstance().findCommentsByBookId(bookId));
        } catch (ServiceException e){
            throw new CommandException("Exception is occurred.", e);
        }
        return PageConstant.VIEW_SINGLE;
    }
}
