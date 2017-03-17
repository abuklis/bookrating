package by.epam.bookrating.controller.command.impl.book;
import by.epam.bookrating.controller.command.Command;
import by.epam.bookrating.controller.constant.PageConstant;
import by.epam.bookrating.controller.constant.ParameterConstant;
import by.epam.bookrating.controller.exception.CommandException;
import by.epam.bookrating.service.exception.ServiceException;
import by.epam.bookrating.service.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
/**
 * Created by anyab on 06.11.16.
 */
public class DeleteBookFromFavoriteCommand implements Command {
    private DeleteBookFromFavoriteCommand(){}
    private static DeleteBookFromFavoriteCommand instance = new DeleteBookFromFavoriteCommand();
    public static DeleteBookFromFavoriteCommand getInstance() {
        return instance;
    }
    private static final Logger logger = Logger.getLogger(DeleteBookFromFavoriteCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        if (isUser(request)){
            String login = request.getParameter(ParameterConstant.LOGIN);
            long bookId = Long.parseLong(request.getParameter(ParameterConstant.BOOK_ID));
            long userId = Long.parseLong(request.getSession().getAttribute(ParameterConstant.USER_ID).toString());
            try {
                UserService.getInstance().deleteBookFromFavorite(userId, bookId);
                request.setAttribute(ParameterConstant.BOOK_LIST, UserService.getInstance().findFavoriteBooks(userId));
            } catch (ServiceException e) {
                throw new CommandException("Exception in this command", e);
            }
            return PageConstant.FAVORITE_BOOKS;
        } else {
            return PageConstant.ERROR_PAGE;
        }

    }
}
