package by.epam.bookrating.command.impl.book;
import by.epam.bookrating.command.Command;
import by.epam.bookrating.command.PageConstant;
import static by.epam.bookrating.command.ParameterConstant.*;
import by.epam.bookrating.command.CommandException;
import by.epam.bookrating.service.ServiceException;
import by.epam.bookrating.service.BookService;
import by.epam.bookrating.service.CommentService;
import by.epam.bookrating.service.RatingService;
import by.epam.bookrating.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
/**
 * Class {@code EmptyCommand} is the class, that implements {@code ICommand} interface to
 * forward to a main page.
 * @author Anna Buklis
 */

public class ViewSingleBookCommand implements Command {
    private ViewSingleBookCommand(){}
    private static ViewSingleBookCommand instance = new ViewSingleBookCommand();
    public static ViewSingleBookCommand getInstance() {
        return instance;
    }
    private static Logger logger = Logger.getLogger(ViewSingleBookCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        long bookId = Long.parseLong(request.getParameter(BOOK_ID));
        logger.info("Received bookId = " + bookId);
        try {
            request.setAttribute(BOOK_ITEM, BookService.getInstance().viewBookInfo(bookId));
            request.setAttribute(COMMENTS, CommentService.getInstance().findCommentsByBookId(bookId));
            request.setAttribute(AVG_RATING, RatingService.getInstance().findAvgRatingByBookId(bookId));
            if (isUser(request)) {
                long userId = Long.parseLong(request.getSession().getAttribute(USER_ID).toString());
                int rating = RatingService.getInstance().checkRating(bookId, userId);
                request.setAttribute(RATING, rating);
                if (UserService.getInstance().isBookInRead(userId, bookId)){
                    request.setAttribute(IN_READ, YES);
                }   if (UserService.getInstance().isBookInFavorite(userId, bookId)){
                    request.setAttribute(IN_FAVORITE, YES);
                }
            }
        } catch (ServiceException e) {
            throw new CommandException("Exception in LoginCommand.", e);
        }
        return PageConstant.VIEW_SINGLE;
    }
}

