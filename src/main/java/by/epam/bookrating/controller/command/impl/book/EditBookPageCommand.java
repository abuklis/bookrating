package by.epam.bookrating.controller.command.impl.book;
import by.epam.bookrating.controller.command.Command;
import by.epam.bookrating.controller.constant.PageConstant;
import by.epam.bookrating.controller.constant.ParameterConstant;
import by.epam.bookrating.controller.exception.CommandException;
import by.epam.bookrating.service.exception.ServiceException;
import by.epam.bookrating.service.service.BookService;
import by.epam.bookrating.service.service.GenreService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

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
    private static final Logger logger = Logger.getLogger(EditBookPageCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        if (isAdmin(request)) {
            try {
                long bookId = Long.parseLong(request.getParameter(ParameterConstant.BOOK_ID));
                request.setAttribute(ParameterConstant.BOOK_ITEM, BookService.getInstance().viewBookInfo(bookId));
                request.setAttribute(ParameterConstant.GENRES, GenreService.getInstance().findAllGenres());
            } catch (ServiceException e) {
                throw new CommandException("Exception occurred while sending book item to a page.", e);
            }
            return PageConstant.EDIT_BOOK_PAGE;
        } else {
            return PageConstant.ERROR_PAGE;
        }
    }
}
