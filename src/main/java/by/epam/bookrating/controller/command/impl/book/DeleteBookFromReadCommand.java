package by.epam.bookrating.controller.command.impl.book;
import by.epam.bookrating.controller.command.Command;
import by.epam.bookrating.controller.constant.PageConstant;
import by.epam.bookrating.controller.constant.ParameterConstant;
import by.epam.bookrating.controller.exception.CommandException;
import by.epam.bookrating.service.exception.ServiceException;
import by.epam.bookrating.service.service.UserService;

import javax.servlet.http.HttpServletRequest;
/**
 * Created by anyab on 28.11.2016.
 */
public class DeleteBookFromReadCommand implements Command {
    private static DeleteBookFromReadCommand instance = new DeleteBookFromReadCommand();
    public static DeleteBookFromReadCommand getInstance() {
        return instance;
    }
    private DeleteBookFromReadCommand() {
    }

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        if (isUser(request)){
            String login = request.getParameter(ParameterConstant.LOGIN);
            long userId = Long.parseLong(request.getSession().getAttribute(ParameterConstant.USER_ID).toString());
            long bookId = Long.parseLong(request.getParameter(ParameterConstant.BOOK_ID));
            try {
                UserService.getInstance().deleteBookFromRead(userId, bookId);
                request.setAttribute(ParameterConstant.BOOK_LIST, UserService.getInstance().findReadBooks(userId));
            } catch (ServiceException e) {
                throw new CommandException("Exception in this command", e);
            }
            return PageConstant.READ_BOOKS;
        } else {
            return PageConstant.ERROR_PAGE;
        }
    }
}
