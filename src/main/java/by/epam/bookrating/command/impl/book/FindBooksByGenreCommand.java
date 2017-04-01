package by.epam.bookrating.command.impl.book;
import by.epam.bookrating.command.Command;
import by.epam.bookrating.command.PageConstant;
import by.epam.bookrating.command.ParameterConstant;
import by.epam.bookrating.command.CommandException;
import by.epam.bookrating.service.BookService;
import by.epam.bookrating.service.GenreService;
import by.epam.bookrating.service.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.epam.bookrating.command.ParameterConstant.*;

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
    private static Logger logger = Logger.getLogger(FindBooksByGenreCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            int genreId = Integer.parseInt(request.getParameter(GENRE));
            logger.info("Find all books with genreId " + genreId);
            request.setAttribute(BOOK_LIST,
                    BookService.getInstance().findBooksByGenre(GENRE, genreId));
            request.setAttribute(GENRE,
                    GenreService.getInstance().findGenreById(genreId).getGenreName());
        } catch (ServiceException e) {
            throw new CommandException("Exception in Find Books By Genre command", e);
        }
        return PageConstant.BOOKS_BY_GENRE;
    }
}
