package by.epam.bookrating.controller.command.impl.comment;
import by.epam.bookrating.controller.command.Command;
import by.epam.bookrating.controller.constant.PageConstant;
import by.epam.bookrating.controller.exception.CommandException;
import by.epam.bookrating.service.exception.ServiceException;
import by.epam.bookrating.service.service.BookService;
import by.epam.bookrating.service.service.CommentService;
import by.epam.bookrating.service.service.RatingService;
import by.epam.bookrating.service.service.UserService;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import static by.epam.bookrating.controller.constant.ParameterConstant.*;

/**
 * Created by anyab on 25.09.16.
 */
public class LeaveCommentCommand implements Command {
    private LeaveCommentCommand(){}
    private static LeaveCommentCommand instance = new LeaveCommentCommand();
    public static LeaveCommentCommand getInstance() {
        return instance;
    }
    private static final Logger logger = Logger.getLogger(LeaveCommentCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        if (isUser(request)){
            long bookId = Long.parseLong(request.getParameter(BOOK_ID));
            long userId = Long.parseLong(request.getParameter(USER_ID));
            long time = System.currentTimeMillis();
            java.sql.Date date = new java.sql.Date(time);
            String commentText = request.getParameter(COMMENT_TEXT);
            try {
                CommentService.getInstance().leaveComment(bookId, userId, date, commentText);
                request.setAttribute(BOOK_ITEM, BookService.getInstance().viewBookInfo(bookId));
                request.setAttribute(AVG_RATING, RatingService.getInstance().findAvgRatingByBookId(bookId));
                request.setAttribute(COMMENTS,
                        CommentService.getInstance().findCommentsByBookId(bookId));
                request.setAttribute(RATING, RatingService.getInstance().checkRating(bookId, userId));
                if (UserService.getInstance().isBookInRead(userId, bookId)){
                    request.setAttribute(IN_READ, YES);
                }   if (UserService.getInstance().isBookInFavorite(userId, bookId)){
                    request.setAttribute(IN_FAVORITE, YES);
                }
            } catch (ServiceException e){
                throw new CommandException("Exception in LeaveComment command.",e);
            }
            return PageConstant.VIEW_SINGLE;
        } else {
            return PageConstant.ERROR_PAGE;
        }
    }
}
