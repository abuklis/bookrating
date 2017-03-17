package by.epam.bookrating.controller.command.impl.book;
import by.epam.bookrating.controller.command.Command;
import by.epam.bookrating.controller.constant.PageConstant;
import by.epam.bookrating.controller.exception.CommandException;
import by.epam.bookrating.service.exception.ServiceException;
import by.epam.bookrating.service.service.BookService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.epam.bookrating.controller.constant.ParameterConstant.*;

/**
 * Created by anyab on 11.09.16.
 */
public class ViewAllBooksCommand implements Command {
    private ViewAllBooksCommand(){}
    private static ViewAllBooksCommand instance = new ViewAllBooksCommand();
    public static ViewAllBooksCommand getInstance() {
        return instance;
    }
    private static final Logger logger = Logger.getLogger(ViewAllBooksCommand.class);


    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try{
            int page = 1;
            int recordsPerPage = 5;
            if(request.getParameter(PAGE) != null)
                page = Integer.parseInt(request.getParameter(PAGE));
            request.setAttribute(BOOK_LIST, BookService.getInstance().viewAllBooks((page-1)*recordsPerPage,
                    recordsPerPage));
            request.setAttribute(POPULAR_BOOKS, BookService.getInstance().findBooksWithHighRating());
            //todo вынести пагинацию в сервисы
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
