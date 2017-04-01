package by.epam.bookrating.command.impl.book;
import by.epam.bookrating.command.Command;
import by.epam.bookrating.command.impl.ConverseToHashMap;
import by.epam.bookrating.command.PageConstant;
import by.epam.bookrating.command.ParameterConstant;
import by.epam.bookrating.command.CommandException;
import by.epam.bookrating.service.ServiceException;
import by.epam.bookrating.service.BookService;
import by.epam.bookrating.service.CommentService;
import by.epam.bookrating.service.RatingService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;

import static by.epam.bookrating.command.ParameterConstant.*;


/**
 * Created by anyab on 22.09.16.
 */
public class EditBookCommand implements Command {
    private EditBookCommand(){}
    private static EditBookCommand instance = new EditBookCommand();
    public static EditBookCommand getInstance() {
        return instance;
    }
    private static Logger logger = Logger.getLogger(EditBookCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        if (isAdmin(request)) {
            Enumeration<String> parameters = request.getParameterNames();
            String[] genres = request.getParameterValues(GENRE);
            HashMap<String, String> toService = ConverseToHashMap.createParametersMap(parameters,request);
            Part imgPart = null;
            try {
                imgPart = request.getPart(NEW_IMAGE_URL);
            } catch (IOException | ServletException e) {
                logger.warn("Exception occurred while getting image part from jsp.");
            }
            try{
                long bookId = Long.parseLong(toService.get(BOOK_ID));
                String absoluteDiskPath = request.getServletContext().getRealPath(ABSOLUTE_PATH);
                BookService.getInstance().editBook(toService, imgPart, absoluteDiskPath, genres);
                request.setAttribute(BOOK_ITEM, BookService.getInstance().viewBookInfo(bookId));
                request.setAttribute(COMMENTS, CommentService.getInstance().findCommentsByBookId(bookId));
                request.setAttribute(AVG_RATING,
                        RatingService.getInstance().findAvgRatingByBookId(bookId));
            } catch ( ServiceException e) {
                throw new CommandException("Exception in EditBookCommand.", e);
            }
            return PageConstant.VIEW_SINGLE;
        } else {
            return PageConstant.ERROR_PAGE;
        }
    }
}
