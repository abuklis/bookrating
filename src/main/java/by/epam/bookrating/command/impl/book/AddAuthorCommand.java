package by.epam.bookrating.command.impl.book;
import by.epam.bookrating.command.Command;
import by.epam.bookrating.command.impl.ConverseToHashMap;
import by.epam.bookrating.command.PageConstant;
import by.epam.bookrating.command.ParameterConstant;
import by.epam.bookrating.command.CommandException;
import by.epam.bookrating.service.ServiceException;
import by.epam.bookrating.service.AuthorService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.*;

/**
 * Created by anyab on 06.11.16.
 */
public class AddAuthorCommand implements Command {
    private static AddAuthorCommand addAuthorCommand = new AddAuthorCommand();
    public static AddAuthorCommand getInstance() {
        return addAuthorCommand;
    }
    private AddAuthorCommand() {}
    private static Logger logger = Logger.getLogger(AddAuthorCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        if (isAdmin(request)) {
            Enumeration<String> parameters = request.getParameterNames();
            String absoluteDiskPath = request.getServletContext().getRealPath("/");
            Part imgPart = null;
            String page = PageConstant.MAIN_PAGE;
            try {
                imgPart = request.getPart(ParameterConstant.IMAGE_URL);
            } catch (IOException | ServletException e) {
                logger.warn("Exception during getting image part from jsp.", e);
            }
            try {
                Map<String, String> toService = ConverseToHashMap.createParametersMap(parameters, request);
                if (!AuthorService.getInstance().addAuthor(toService, imgPart, absoluteDiskPath)) {
                    page = PageConstant.ADD_AUTHOR_PAGE;
                    request.setAttribute(ParameterConstant.ERROR_MESSAGE, "error");
                }
            } catch (ServiceException e) {
                throw new CommandException("Exception in AddAuthorCommand.", e);
            }
            return page;
        } else {
            return PageConstant.ERROR_PAGE;
        }
    }
}