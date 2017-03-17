package by.epam.bookrating.controller.command.impl.book;
import by.epam.bookrating.controller.command.Command;
import by.epam.bookrating.controller.constant.PageConstant;
import by.epam.bookrating.controller.constant.ParameterConstant;
import by.epam.bookrating.controller.exception.CommandException;
import by.epam.bookrating.service.exception.ServiceException;
import by.epam.bookrating.service.service.BookService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by anyab on 21.09.16.
 */
public class DeleteBookCommand implements Command {
    private DeleteBookCommand(){}
    private static DeleteBookCommand instance = new DeleteBookCommand();
    public static DeleteBookCommand getInstance() {
        return instance;
    }
    private static final Logger logger = Logger.getLogger(DeleteBookCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        if (isAdmin(request)) {
            try {
                long bookId = Long.parseLong(request.getParameter(ParameterConstant.BOOK_ID));
                logger.info("Command received bookId = " + bookId);
                BookService.getInstance().deleteBook(bookId);
            } catch (ServiceException e) {
                throw new CommandException("Exception in DeleteBookCommand.", e);
            }
            return PageConstant.MAIN_PAGE;
        } else {
            return PageConstant.ERROR_PAGE;
        }
    }
}
