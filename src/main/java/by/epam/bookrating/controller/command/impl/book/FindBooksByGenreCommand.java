package by.epam.bookrating.controller.command.impl.book;
import by.epam.bookrating.controller.command.Command;
import by.epam.bookrating.controller.constant.PageConstant;
import by.epam.bookrating.controller.constant.ParameterConstant;
import by.epam.bookrating.controller.exception.CommandException;
import by.epam.bookrating.service.exception.ServiceException;
import by.epam.bookrating.service.service.*;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
/**
 * Created by anyab on 22.10.16.
 */
public class FindBooksByGenreCommand implements Command {
    private static FindBooksByGenreCommand instance = new FindBooksByGenreCommand();
    public static FindBooksByGenreCommand getInstance() {
        return instance;
    }
    private FindBooksByGenreCommand() {
    }
    private static final Logger logger = Logger.getLogger(FindBooksByGenreCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            int genreId = Integer.parseInt(request.getParameter(ParameterConstant.GENRE));
            logger.info("Find all books with genreId " + genreId);
            request.setAttribute(ParameterConstant.BOOK_LIST,
                    BookService.getInstance().findBooksByGenre(ParameterConstant.GENRE, genreId));
            request.setAttribute(ParameterConstant.GENRE,
                    GenreService.getInstance().findGenreById(genreId).getGenreName());
        } catch (ServiceException e) {
            throw new CommandException("Exception in Find Books By Genre command", e);
        }
        return PageConstant.BOOKS_BY_GENRE;
    }
}
