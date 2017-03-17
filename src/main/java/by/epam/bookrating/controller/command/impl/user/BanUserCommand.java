package by.epam.bookrating.controller.command.impl.comment;
import by.epam.bookrating.controller.command.Command;
import by.epam.bookrating.controller.constant.PageConstant;
import by.epam.bookrating.controller.constant.ParameterConstant;
import by.epam.bookrating.controller.exception.CommandException;
import by.epam.bookrating.service.exception.ServiceException;
import by.epam.bookrating.service.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by anyab on 29.10.16.
 */
public class BanUserCommand implements Command {
    private static BanUserCommand instance = new BanUserCommand();
    public static BanUserCommand getInstance() {
        return instance;
    }
    private BanUserCommand() {
    }
    private static final Logger logger = Logger.getLogger(BanUserCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        if (isAdmin(request)) {
            long userId = Long.parseLong(String.valueOf(request.getParameter(ParameterConstant.USER_ID)));
            try {
                UserService.getInstance().banUser(userId);
                request.setAttribute(ParameterConstant.USERS, UserService.getInstance().viewAllUsers());
                return PageConstant.VIEW_USERS_INFO_PAGE;
            } catch (ServiceException e) {
                throw new CommandException("Exception in this command.", e);
            }
        } else {
            return PageConstant.ERROR_PAGE;
        }
    }
}
