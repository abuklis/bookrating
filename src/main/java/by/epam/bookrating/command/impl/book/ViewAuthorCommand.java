package by.epam.bookrating.command.impl.book;
import by.epam.bookrating.command.Command;
import by.epam.bookrating.command.PageConstant;
import by.epam.bookrating.command.ParameterConstant;
import by.epam.bookrating.command.CommandException;
import by.epam.bookrating.service.ServiceException;
import by.epam.bookrating.service.AuthorService;
import by.epam.bookrating.service.BookService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.epam.bookrating.command.ParameterConstant.*;

/**
 * Created by anyab on 13.11.16.
 */
public class ViewAuthorCommand implements Command {
    private static ViewAuthorCommand instance = new ViewAuthorCommand();
    public static ViewAuthorCommand getInstance() {
        return instance;
    }
    private ViewAuthorCommand() {}
    private static Logger logger = Logger.getLogger(ViewAuthorCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        long authorId = Long.parseLong(request.getParameter(AUTHOR_ID));
        logger.info("Received authorId = " + authorId);
        try {
            request.setAttribute(AUTHOR, AuthorService.getInstance().findAuthorById(authorId));
            request.setAttribute(BOOK_LIST,
                    BookService.getInstance().findBooksByAuthorId(AUTHOR, authorId));
        } catch (ServiceException e) {
            throw new CommandException("Exception in LoginCommand.", e);
        }
        return PageConstant.VIEW_AUTHOR;
    }
}
