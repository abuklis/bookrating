package by.epam.bookrating.controller.command.impl.user;
import by.epam.bookrating.controller.command.Command;
import by.epam.bookrating.controller.constant.PageConstant;
import by.epam.bookrating.controller.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
/**
 * Created by anyab on 22.09.16.
 */
public class RegistrationPageCommand  implements Command {
    private RegistrationPageCommand(){}
    private static RegistrationPageCommand instance = new RegistrationPageCommand();
    public static RegistrationPageCommand getInstance() {
        return instance;
    }

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        return PageConstant.REGISTRATION_PAGE;
    }
}
