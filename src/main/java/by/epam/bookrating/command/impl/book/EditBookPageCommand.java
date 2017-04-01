package by.epam.bookrating.command.impl.book;
import by.epam.bookrating.command.Command;
import by.epam.bookrating.command.PageConstant;
import by.epam.bookrating.command.ParameterConstant;
import by.epam.bookrating.command.CommandException;
import by.epam.bookrating.service.ServiceException;
import by.epam.bookrating.service.BookService;
import by.epam.bookrating.service.GenreService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.epam.bookrating.command.ParameterConstant.*;

/**
 * Created by anyab on 22.09.16.
 */
public class EditBookPageCommand implements Command {
    private EditBookPageCommand() {
    }
    private static EditBookPageCommand instance = new EditBookPageCommand();
    public static EditBookPageCommand getInstance() {
        return instance;
    }
    private static Logger logger = Logger.getLogger(EditBookPageCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        if (isAdmin(request)) {
            try {
                long bookId = Long.parseLong(request.getParameter(BOOK_ID));
                request.setAttribute(BOOK_ITEM, BookService.getInstance().viewBookInfo(bookId));
                request.setAttribute(GENRES, GenreService.getInstance().findAllGenres());
            } catch (ServiceException e) {
                throw new CommandException("Exception occurred while sending book item to a page.", e);
            }
            return PageConstant.EDIT_BOOK_PAGE;
        } else {
            return PageConstant.ERROR_PAGE;
        }
    }
}
