package by.epam.bookrating.command.impl.user;
import by.epam.bookrating.command.Command;
import by.epam.bookrating.command.PageConstant;
import by.epam.bookrating.command.ParameterConstant;
import by.epam.bookrating.command.CommandException;
import by.epam.bookrating.service.ServiceException;
import by.epam.bookrating.service.UserService;

import javax.servlet.http.HttpServletRequest;
/**
 * Created by anyab on 28.11.2016.
 */
public class ViewReadBooksCommand implements Command {
    private static ViewReadBooksCommand instance = new ViewReadBooksCommand();
    public static ViewReadBooksCommand getInstance() {
        return instance;
    }
    private ViewReadBooksCommand() {
    }

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        if (isUser(request)){
            long userId = Long.parseLong(request.getParameter(ParameterConstant.USER_ID));
            try {
                request.setAttribute(ParameterConstant.BOOK_LIST, UserService.getInstance().findReadBooks(userId));
                request.setAttribute(ParameterConstant.USER_ID, userId);
            } catch (ServiceException e) {
                throw new CommandException("Exception", e);
            }
            return PageConstant.READ_BOOKS;
        } else {
            return PageConstant.LOGIN_PAGE;
        }

    }
}
