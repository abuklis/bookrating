package by.epam.bookrating.controller.command.impl.book;
import by.epam.bookrating.controller.command.Command;
import by.epam.bookrating.controller.constant.PageConstant;
import by.epam.bookrating.controller.constant.ParameterConstant;
import by.epam.bookrating.controller.exception.CommandException;
import by.epam.bookrating.service.exception.ServiceException;
import by.epam.bookrating.service.service.AuthorService;
import by.epam.bookrating.service.service.BookService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by anyab on 13.11.16.
 */
public class ViewAuthorCommand implements Command {
    private static ViewAuthorCommand instance = new ViewAuthorCommand();
    public static ViewAuthorCommand getInstance() {
        return instance;
    }
    private ViewAuthorCommand() {
    }
    private static final Logger logger = Logger.getLogger(ViewAuthorCommand.class);


    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        int authorId = Integer.parseInt(request.getParameter(ParameterConstant.AUTHOR_ID));
        logger.info("Received authorId = " + authorId);
        try {
            request.setAttribute(ParameterConstant.AUTHOR, AuthorService.getInstance().findAuthorById(authorId));
            request.setAttribute(ParameterConstant.BOOK_LIST,
                    BookService.getInstance().findBooksByAuthorId(ParameterConstant.AUTHOR, authorId));
        } catch (ServiceException e) {
            throw new CommandException("Exception in LoginCommand.", e);
        }
        return PageConstant.VIEW_AUTHOR;
    }
}
