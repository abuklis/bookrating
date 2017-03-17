package by.epam.bookrating.controller.command.impl.user;
import by.epam.bookrating.controller.command.Command;
import by.epam.bookrating.controller.constant.PageConstant;
import by.epam.bookrating.controller.constant.ParameterConstant;
import by.epam.bookrating.controller.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
/**
 * Created by anyab on 25.09.16.
 */
public class LoginPageCommand implements Command {
    private LoginPageCommand(){}
    private static LoginPageCommand instance = new LoginPageCommand();
    public static LoginPageCommand getInstance() {
        return instance;
    }

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        request.getSession().setAttribute(ParameterConstant.PAGE,
                request.getParameter(ParameterConstant.PAGE));
        return PageConstant.LOGIN_PAGE;
    }
}
