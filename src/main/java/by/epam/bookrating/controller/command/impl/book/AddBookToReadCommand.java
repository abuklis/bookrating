package by.epam.bookrating.controller.command.impl.book;
import by.epam.bookrating.controller.command.Command;
import by.epam.bookrating.controller.constant.PageConstant;
import by.epam.bookrating.controller.constant.ParameterConstant;
import by.epam.bookrating.controller.exception.CommandException;
import by.epam.bookrating.service.exception.ServiceException;
import by.epam.bookrating.service.service.BookService;
import by.epam.bookrating.service.service.CommentService;
import by.epam.bookrating.service.service.RatingService;
import by.epam.bookrating.service.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by anyab on 04.11.16.
 */
public class AddBookToReadCommand implements Command {
    private static AddBookToReadCommand instance = new AddBookToReadCommand();
    public static AddBookToReadCommand getInstance() {
        return instance;
    }
    private AddBookToReadCommand() {}
    private static final Logger logger = Logger.getLogger(AddBookToReadCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        if (isUser(request)){
            String login = request.getParameter(ParameterConstant.LOGIN);
            long userId = Long.parseLong(request.getParameter(ParameterConstant.USER_ID));
            long bookId = Long.parseLong(request.getParameter(ParameterConstant.BOOK_ID));
            logger.info("Command received login = "+ login+ " and bookId= "+ bookId);
            // if (&&bookId>=0){
            try {
                UserService.getInstance().addBookToRead(userId, bookId);
                request.setAttribute(ParameterConstant.BOOK_ITEM, BookService.getInstance().viewBookInfo(bookId));
                request.setAttribute(ParameterConstant.COMMENTS,
                        CommentService.getInstance().findCommentsByBookId(bookId));
                request.setAttribute(ParameterConstant.AVG_RATING,
                        RatingService.getInstance().findAvgRatingByBookId(bookId));
                request.setAttribute(ParameterConstant.RATING, RatingService.getInstance().checkRating(bookId, userId));
                if (UserService.getInstance().isBookInFavorite(userId, bookId)){
                    request.setAttribute(ParameterConstant.IN_FAVORITE, ParameterConstant.YES);
                }
                request.setAttribute(ParameterConstant.IN_READ, ParameterConstant.YES);
            } catch (ServiceException e) {
                throw new CommandException("Exception in this command.", e);
            }
            return PageConstant.VIEW_SINGLE;
        } else {
            return PageConstant.ERROR_PAGE;
        }

    }
}
