package by.epam.bookrating.command.impl.user;
import by.epam.bookrating.command.Command;
import by.epam.bookrating.command.PageConstant;
import by.epam.bookrating.command.CommandException;

import javax.servlet.http.HttpServletRequest;


/**
 * Command that logouts user<br/>
 * Implements {@link by.epam.bookrating.command.Command}
 * @author anyab
 */
public class LogoutCommand implements Command {
    private LogoutCommand(){}
    private static LogoutCommand instance = new LogoutCommand();
    public static LogoutCommand getInstance() {
        return instance;
    }

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        request.getSession().invalidate();
        return PageConstant.LOGIN_PAGE;
    }
}
