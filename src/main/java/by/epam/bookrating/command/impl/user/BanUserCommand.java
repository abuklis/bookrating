package by.epam.bookrating.command.impl.user;
import by.epam.bookrating.command.Command;
import by.epam.bookrating.command.PageConstant;
import by.epam.bookrating.command.ParameterConstant;
import by.epam.bookrating.command.CommandException;
import by.epam.bookrating.service.ServiceException;
import by.epam.bookrating.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.epam.bookrating.command.ParameterConstant.*;

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
    private static Logger logger = Logger.getLogger(BanUserCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        if (isAdmin(request)) {
            long userId = Long.parseLong(String.valueOf(request.getParameter(USER_ID)));
            try {
                UserService.getInstance().banUser(userId);
                request.setAttribute(USERS, UserService.getInstance().viewAllUsers());
                return PageConstant.VIEW_USERS_INFO_PAGE;
            } catch (ServiceException e) {
                throw new CommandException("Exception in this command.", e);
            }
        } else {
            return PageConstant.ERROR_PAGE;
        }
    }
}
