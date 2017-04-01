package by.epam.bookrating.command.impl.book;
import by.epam.bookrating.command.Command;
import by.epam.bookrating.command.PageConstant;
import by.epam.bookrating.command.ParameterConstant;
import by.epam.bookrating.command.CommandException;
import by.epam.bookrating.service.ServiceException;
import by.epam.bookrating.service.BookService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.epam.bookrating.command.ParameterConstant.*;


/**
 * Created by anyab on 21.09.16.
 */
public class DeleteBookCommand implements Command {
    private DeleteBookCommand(){}
    private static DeleteBookCommand instance = new DeleteBookCommand();
    public static DeleteBookCommand getInstance() {
        return instance;
    }
    private static Logger logger = Logger.getLogger(DeleteBookCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        if (isAdmin(request)) {
            try {
                long bookId = Long.parseLong(request.getParameter(BOOK_ID));
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
