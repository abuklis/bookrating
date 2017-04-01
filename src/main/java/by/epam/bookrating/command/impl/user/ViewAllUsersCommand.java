package by.epam.bookrating.command.impl.user;
import by.epam.bookrating.command.Command;
import by.epam.bookrating.command.PageConstant;
import by.epam.bookrating.command.ParameterConstant;
import by.epam.bookrating.command.CommandException;
import by.epam.bookrating.service.ServiceException;
import by.epam.bookrating.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;


public class ViewAllUsersCommand implements Command {
    private static ViewAllUsersCommand instance = new ViewAllUsersCommand();
    public static ViewAllUsersCommand getInstance() {
        return instance;
    }
    private ViewAllUsersCommand() {
    }
    private static Logger logger = Logger.getLogger(ViewAllUsersCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        if (isAdmin(request)) {
            try {
                request.setAttribute(ParameterConstant.USERS, UserService.getInstance().viewAllUsers());
            } catch (ServiceException e) {
                throw new CommandException("Exception in command.", e);
            }
            logger.info("Command finished its work.");
            return PageConstant.VIEW_USERS_INFO_PAGE;
        } else {
            return PageConstant.ERROR_PAGE;
        }
    }
}
