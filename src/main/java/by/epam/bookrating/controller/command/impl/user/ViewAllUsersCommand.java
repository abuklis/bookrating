package by.epam.bookrating.controller.command.impl.user;
import by.epam.bookrating.controller.command.Command;
import by.epam.bookrating.controller.constant.PageConstant;
import by.epam.bookrating.controller.constant.ParameterConstant;
import by.epam.bookrating.controller.exception.CommandException;
import by.epam.bookrating.service.exception.ServiceException;
import by.epam.bookrating.service.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;


public class ViewAllUsersCommand implements Command {
    private static ViewAllUsersCommand instance = new ViewAllUsersCommand();
    public static ViewAllUsersCommand getInstance() {
        return instance;
    }
    private ViewAllUsersCommand() {
    }
    private static final Logger logger = Logger.getLogger(ViewAllUsersCommand.class);

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
