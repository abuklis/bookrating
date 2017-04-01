package by.epam.bookrating.command.impl.book;
import by.epam.bookrating.command.Command;
import by.epam.bookrating.command.PageConstant;
import by.epam.bookrating.command.CommandException;
import by.epam.bookrating.entity.Book;
import by.epam.bookrating.service.ServiceException;
import by.epam.bookrating.service.BookService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static by.epam.bookrating.command.ParameterConstant.*;

/**
 * Created by anyab on 11.09.16.
 */
public class ViewAllBooksCommand implements Command {
    private ViewAllBooksCommand(){}
    private static ViewAllBooksCommand instance = new ViewAllBooksCommand();
    public static ViewAllBooksCommand getInstance() {
        return instance;
    }
    private static Logger logger = Logger.getLogger(ViewAllBooksCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try{
            int page = 1;
            int recordsPerPage = 5;
            if(request.getParameter(PAGE) != null)
                page = Integer.parseInt(request.getParameter(PAGE));
            List<Book> partOfBookList = BookService.getInstance().viewAllBooks((page-1)*recordsPerPage,
                    recordsPerPage);
            request.setAttribute(BOOK_LIST, partOfBookList);
            request.setAttribute(POPULAR_BOOKS, BookService.getInstance().findBooksWithHighRating());

            int totalAmount = BookService.getInstance().getAmountOfBooks();
            int pagesAmount = (int) Math.ceil(totalAmount * 1.0 / recordsPerPage);
            request.setAttribute(PAGES_AMOUNT, pagesAmount);
            request.setAttribute(CUR_PAGE_NUMBER, page);
        } catch (ServiceException e){
            logger.trace(e);
            throw new CommandException("Exception in ViewAllBooksCommand.", e);
        }
        return PageConstant.BOOK_LIST_PAGE;
    }
}
