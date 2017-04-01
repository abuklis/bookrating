package by.epam.bookrating.command.impl.book;
import by.epam.bookrating.command.Command;
import by.epam.bookrating.command.impl.ConverseToHashMap;
import by.epam.bookrating.command.PageConstant;
import by.epam.bookrating.command.ParameterConstant;
import by.epam.bookrating.command.CommandException;
import by.epam.bookrating.service.ServiceException;
import by.epam.bookrating.service.BookService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

public class AddBookCommand extends ConverseToHashMap implements Command {
    private AddBookCommand() {}
    private final static AddBookCommand instance = new AddBookCommand();
    public static AddBookCommand getInstance() {
        return instance;
    }
    private static Logger logger = Logger.getLogger(AddBookCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        if (isAdmin(request)) {
            Enumeration<String> parameters = request.getParameterNames();
            String[] genres = request.getParameterValues(ParameterConstant.GENRE);
            String absoluteDiskPath = request.getServletContext().getRealPath(ParameterConstant.ABSOLUTE_PATH);
            Part imgPart = null;
            try {
                imgPart = request.getPart(ParameterConstant.IMAGE_URL);
            } catch (IOException | ServletException e) {
                logger.warn("Exception during getting image part from jsp.");
            }
            try {
                Map<String, String> toService = createParametersMap(parameters, request);
                BookService.getInstance().addBook(toService, imgPart, absoluteDiskPath, genres);
            } catch (ServiceException e) {
                throw new CommandException("Exception in AddBookCommand.", e);
            }
            return PageConstant.MAIN_PAGE;
        } else {
            return PageConstant.ERROR_PAGE;
        }
    }
}
