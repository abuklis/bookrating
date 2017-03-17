package by.epam.bookrating.controller.command.impl.book;
import by.epam.bookrating.controller.command.Command;
import by.epam.bookrating.controller.constant.PageConstant;
import by.epam.bookrating.controller.constant.ParameterConstant;
import by.epam.bookrating.controller.exception.CommandException;
import by.epam.bookrating.service.exception.ServiceException;
import by.epam.bookrating.service.service.GenreService;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by anyab on 20.09.16.
 */
public class AddBookPageCommand implements Command {
    private AddBookPageCommand() {}
    private static AddBookPageCommand instance = new AddBookPageCommand();
    public static AddBookPageCommand getInstance() {
        return instance;
    }

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        if (isAdmin(request)) {
            try {
                request.setAttribute(ParameterConstant.GENRES, GenreService.getInstance().findAllGenres());
            } catch (ServiceException e) {
                throw new CommandException("Exception in AddBookPage command");
            }
            return PageConstant.ADD_BOOK_PAGE;
        } else {
            return PageConstant.ERROR_PAGE;
        }
    }
}
