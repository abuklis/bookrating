package by.epam.bookrating.controller.command.impl.book;
import by.epam.bookrating.controller.command.Command;
import by.epam.bookrating.controller.command.impl.ConverseToHashMap;
import by.epam.bookrating.controller.constant.PageConstant;
import by.epam.bookrating.controller.constant.ParameterConstant;
import by.epam.bookrating.controller.exception.CommandException;
import by.epam.bookrating.service.exception.ServiceException;
import by.epam.bookrating.service.service.*;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * Created by anyab on 23.11.2016.
 */
public class EditAuthorCommand implements Command {
    private static EditAuthorCommand instance = new EditAuthorCommand();
    public static EditAuthorCommand getInstance() {
        return instance;
    }
    private EditAuthorCommand() {}
    private static final Logger logger = Logger.getLogger(EditAuthorCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        if (isAdmin(request)) {
            Enumeration<String> parameters = request.getParameterNames();
            HashMap<String, String> toService = ConverseToHashMap.createParametersMap(parameters, request);
            Part imgPart = null;
            try {
                imgPart = request.getPart(ParameterConstant.NEW_IMAGE_URL);
            } catch (IOException | ServletException e) {
                logger.warn("Exception occurred during getting image from JSP.");
            }
            try {
                String absoluteDiskPath = request.getServletContext().getRealPath(ParameterConstant.ABSOLUTE_PATH);
                AuthorService.getInstance().editAuthor(toService, imgPart, absoluteDiskPath);
                long authorId = Long.parseLong(request.getParameter(ParameterConstant.AUTHOR_ID));
                request.setAttribute(ParameterConstant.AUTHOR, AuthorService.getInstance().findAuthorById(authorId));
                request.setAttribute(ParameterConstant.BOOK_LIST,
                        BookService.getInstance().findBooksByAuthorId(ParameterConstant.AUTHOR, authorId));
            } catch (ServiceException e) {
                throw new CommandException("Exception in EditBookCommand.", e);
            }
            return PageConstant.VIEW_AUTHOR;
        } else {
            return PageConstant.ERROR_PAGE;
        }
    }
}
