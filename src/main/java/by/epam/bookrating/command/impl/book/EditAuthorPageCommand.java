package by.epam.bookrating.command.impl.book;
import by.epam.bookrating.command.Command;
import by.epam.bookrating.command.PageConstant;
import by.epam.bookrating.command.ParameterConstant;
import by.epam.bookrating.command.CommandException;
import by.epam.bookrating.service.AuthorService;
import by.epam.bookrating.service.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by anyab on 23.11.2016.
 */
public class EditAuthorPageCommand implements Command {
    private EditAuthorPageCommand(){}
    private static EditAuthorPageCommand instance = new EditAuthorPageCommand();
    public static EditAuthorPageCommand getInstance() {
        return instance;
    }
    private static Logger logger = Logger.getLogger(EditAuthorPageCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        if (isAdmin(request)) {
            try {
                long authorId = Long.parseLong(request.getParameter(ParameterConstant.AUTHOR_ID));
                logger.info("Command received authorId = " + authorId);
                request.setAttribute(ParameterConstant.AUTHOR, AuthorService.getInstance().findAuthorById(authorId));
            } catch (ServiceException e) {
                throw new CommandException("Exception in Command Layer", e);
            }
            return PageConstant.EDIT_AUTHOR_PAGE;
        } else {
            return PageConstant.ERROR_PAGE;
        }
    }
}
