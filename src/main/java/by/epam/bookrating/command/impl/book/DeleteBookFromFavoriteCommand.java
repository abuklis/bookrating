package by.epam.bookrating.command.impl.book;
import by.epam.bookrating.command.Command;
import by.epam.bookrating.command.PageConstant;
import by.epam.bookrating.command.ParameterConstant;
import by.epam.bookrating.command.CommandException;
import by.epam.bookrating.entity.Book;
import by.epam.bookrating.service.ServiceException;
import by.epam.bookrating.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by anyab on 06.11.16.
 */
public class DeleteBookFromFavoriteCommand implements Command {
    private DeleteBookFromFavoriteCommand(){}
    private static DeleteBookFromFavoriteCommand instance = new DeleteBookFromFavoriteCommand();
    public static DeleteBookFromFavoriteCommand getInstance() {
        return instance;
    }
    private static Logger logger = Logger.getLogger(DeleteBookFromFavoriteCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        if (isUser(request)){
            long bookId = Long.parseLong(request.getParameter(ParameterConstant.BOOK_ID));
            long userId = Long.parseLong(request.getSession().getAttribute(ParameterConstant.USER_ID).toString());
            try {
                UserService.getInstance().deleteBookFromFavorite(userId, bookId);
                List<Book> favoriteBooks = UserService.getInstance().findFavoriteBooks(userId);
                request.setAttribute(ParameterConstant.BOOK_LIST, favoriteBooks);
            } catch (ServiceException e) {
                throw new CommandException("Exception in this command", e);
            }
            return PageConstant.FAVORITE_BOOKS;
        } else {
            return PageConstant.ERROR_PAGE;
        }

    }
}
