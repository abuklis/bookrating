package by.epam.bookrating.controller.command.impl.book;
import by.epam.bookrating.controller.command.Command;
import by.epam.bookrating.controller.constant.PageConstant;
import by.epam.bookrating.controller.constant.ParameterConstant;
import by.epam.bookrating.controller.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
/**
 * Created by anyab on 23.10.16.
 */
public class FindBooksByAuthorCommand implements Command {
    private static FindBooksByAuthorCommand instance = new FindBooksByAuthorCommand();
    public static FindBooksByAuthorCommand getInstance() {
        return instance;
    }
    private FindBooksByAuthorCommand() {
    }


    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String author = request.getParameter(ParameterConstant.AUTHOR);
//        try {
//            request.setAttribute(ParameterConstant.BOOK_LIST,
//                    BookService.getInstance().findBooksByGenre(ParameterConstant.AUTHOR, author));
//        } catch (ServiceException e) {
//            throw new CommandException("Exception in Find Books By Author command", e);
//        }
        request.setAttribute(ParameterConstant.AUTHOR, author);
        return PageConstant.BOOKS_BY_GENRE;
    }
}
