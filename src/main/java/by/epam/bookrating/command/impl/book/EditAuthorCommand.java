package by.epam.bookrating.command.impl.book;
import by.epam.bookrating.command.Command;
import by.epam.bookrating.command.impl.ConverseToHashMap;
import by.epam.bookrating.command.PageConstant;
import by.epam.bookrating.command.ParameterConstant;
import by.epam.bookrating.command.CommandException;
import by.epam.bookrating.service.AuthorService;
import by.epam.bookrating.service.BookService;
import by.epam.bookrating.service.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;

import static by.epam.bookrating.command.ParameterConstant.*;

/**
 * Created by anyab on 23.11.2016.
 */
public class EditAuthorCommand implements Command {
    private static EditAuthorCommand instance = new EditAuthorCommand();
    public static EditAuthorCommand getInstance() {
        return instance;
    }
    private EditAuthorCommand() {}
    private static Logger logger = Logger.getLogger(EditAuthorCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        if (isAdmin(request)) {
            Enumeration<String> parameters = request.getParameterNames();
            HashMap<String, String> toService = ConverseToHashMap.createParametersMap(parameters, request);
            Part imgPart = null;
            try {
                imgPart = request.getPart(NEW_IMAGE_URL);
            } catch (IOException | ServletException e) {
                logger.warn("Exception occurred during getting image from JSP.");
            }
            try {
                String absoluteDiskPath = request.getServletContext().getRealPath(ABSOLUTE_PATH);
                AuthorService.getInstance().editAuthor(toService, imgPart, absoluteDiskPath);
                long authorId = Long.parseLong(request.getParameter(AUTHOR_ID));
                request.setAttribute(AUTHOR, AuthorService.getInstance().findAuthorById(authorId));
                request.setAttribute(BOOK_LIST,
                        BookService.getInstance().findBooksByAuthorId(AUTHOR, authorId));
            } catch (ServiceException e) {
                throw new CommandException("Exception in EditBookCommand.", e);
            }
            return PageConstant.VIEW_AUTHOR;
        } else {
            return PageConstant.ERROR_PAGE;
        }
    }
}
