package by.epam.bookrating.controller.command.impl.user;
import by.epam.bookrating.controller.command.Command;
import by.epam.bookrating.controller.constant.PageConstant;
import by.epam.bookrating.controller.constant.ParameterConstant;
import by.epam.bookrating.controller.exception.CommandException;
import by.epam.bookrating.service.exception.ServiceException;
import by.epam.bookrating.service.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.epam.bookrating.controller.constant.ParameterConstant.*;

/**
 * Created by anyab on 22.10.16.
 */
public class EditProfilePageCommand implements Command {
    private static EditProfilePageCommand ourInstance = new EditProfilePageCommand();
    public static EditProfilePageCommand getInstance() {
        return ourInstance;
    }
    private EditProfilePageCommand() {}
    private static final Logger logger = Logger.getLogger(EditProfilePageCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        if (isUser(request)){
            long userId = Long.parseLong(request.getParameter(USER_ID));
            try {
                request.setAttribute(USER, UserService.getInstance().findUserById(userId));
            } catch (ServiceException e) {
                throw new CommandException("Exception", e);
            }
            return PageConstant.EDIT_PROFILE_PAGE;
        } else {
            return PageConstant.ERROR_PAGE;
        }
    }
}
