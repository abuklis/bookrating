package by.epam.bookrating.controller.command.impl.user;
import by.epam.bookrating.controller.command.Command;
import by.epam.bookrating.controller.constant.PageConstant;
import by.epam.bookrating.controller.constant.ParameterConstant;
import by.epam.bookrating.controller.exception.CommandException;
import by.epam.bookrating.service.exception.ServiceException;
import by.epam.bookrating.service.service.UserService;
import org.apache.log4j.Logger;

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
