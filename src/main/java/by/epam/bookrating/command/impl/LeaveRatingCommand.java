package by.epam.bookrating.command.impl;
import by.epam.bookrating.command.Command;
import by.epam.bookrating.command.PageConstant;
import by.epam.bookrating.command.ParameterConstant;
import by.epam.bookrating.command.CommandException;
import by.epam.bookrating.service.ServiceException;
import by.epam.bookrating.service.BookService;
import by.epam.bookrating.service.CommentService;
import by.epam.bookrating.service.RatingService;
import by.epam.bookrating.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by anyab on 30.09.16.
 */
public class LeaveRatingCommand implements Command {
    private LeaveRatingCommand(){}
    private static LeaveRatingCommand instance = new LeaveRatingCommand();
    public static LeaveRatingCommand getInstance() {
        return instance;
    }
    private static Logger logger = Logger.getLogger(LeaveRatingCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        if (isUser(request)){
            int rating = Integer.parseInt(request.getParameter(ParameterConstant.RATING));
            String login = request.getParameter(ParameterConstant.LOGIN);
            long userId = Long.parseLong(request.getParameter(ParameterConstant.USER_ID));
            long bookId = Long.parseLong(request.getParameter(ParameterConstant.BOOK_ID));
            logger.info("Leaving " + rating + " stars on the book with id = "+ bookId + " by user "+ login);
            try {
                RatingService.getInstance().leaveRating(userId, bookId, rating);
                request.setAttribute(ParameterConstant.BOOK_ITEM, BookService.getInstance().viewBookInfo(bookId));
                request.setAttribute(ParameterConstant.COMMENTS, CommentService.getInstance().findCommentsByBookId(bookId));
                request.setAttribute(ParameterConstant.AVG_RATING, RatingService.getInstance().findAvgRatingByBookId(bookId));
                request.setAttribute(ParameterConstant.RATING, RatingService.getInstance().checkRating(bookId, userId));
                if (UserService.getInstance().isBookInRead(userId, bookId)) {
                    request.setAttribute(ParameterConstant.IN_READ, ParameterConstant.YES);
                } if (UserService.getInstance().isBookInFavorite(userId, bookId)) {
                    request.setAttribute(ParameterConstant.IN_FAVORITE, ParameterConstant.YES);
                }
            } catch (ServiceException e) {
                throw new CommandException("Exception in Leave Rating Command ", e);
            }
            return PageConstant.VIEW_SINGLE;
        } else {
            return PageConstant.ERROR_PAGE;
        }

    }
}
