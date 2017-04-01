package by.epam.bookrating.command.impl.book;
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

public class AddBookToFavoriteCommand implements Command {
    private AddBookToFavoriteCommand(){}
    private static AddBookToFavoriteCommand instance = new AddBookToFavoriteCommand();
    public static AddBookToFavoriteCommand getInstance() {
        return instance;
    }
    private static Logger logger = Logger.getLogger(AddBookToFavoriteCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        if (isUser(request)){
            String login = request.getParameter(ParameterConstant.LOGIN);
            long userId = Long.parseLong(request.getParameter(ParameterConstant.USER_ID));
            long bookId = Long.parseLong(request.getParameter(ParameterConstant.BOOK_ID));
            logger.info("Command received login = "+ login+ " and bookId= "+ bookId);
            try {
                UserService.getInstance().addBookToFavorite(userId, bookId);
                request.setAttribute(ParameterConstant.BOOK_ITEM, BookService.getInstance().viewBookInfo(bookId));
                request.setAttribute(ParameterConstant.COMMENTS, CommentService.getInstance().findCommentsByBookId(bookId));
                request.setAttribute(ParameterConstant.AVG_RATING, RatingService.getInstance().findAvgRatingByBookId(bookId));
                request.setAttribute(ParameterConstant.RATING, RatingService.getInstance().checkRating(bookId, userId));
                if (UserService.getInstance().isBookInRead(userId, bookId)){
                    request.setAttribute(ParameterConstant.IN_READ, ParameterConstant.YES);
                }
                request.setAttribute(ParameterConstant.IN_FAVORITE, ParameterConstant.YES);
                logger.info("Book added to favorite.");
            } catch (ServiceException e) {
                throw new CommandException("Exception in this command.", e);
            }
            return PageConstant.VIEW_SINGLE;
        } else {
            return PageConstant.ERROR_PAGE;
        }
    }
}
