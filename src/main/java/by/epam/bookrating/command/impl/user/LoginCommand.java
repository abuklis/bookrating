package by.epam.bookrating.command.impl.user;
import by.epam.bookrating.command.Command;
import by.epam.bookrating.command.PageConstant;
import by.epam.bookrating.command.CommandException;
import by.epam.bookrating.service.ServiceException;
import by.epam.bookrating.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.epam.bookrating.command.ParameterConstant.*;

public class LoginCommand implements Command {

    private LoginCommand() {}
    private static LoginCommand instance = new LoginCommand();
    public static LoginCommand getInstance() {
        return instance;
    }
    private static Logger logger = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        //String page = String.valueOf(request.getSession().getAttribute(ParameterConstant.PAGE));
        String page = PageConstant.MAIN_PAGE;
        try {
            // проверить, если такой пользователь уже в сессии (мало ли вошёл в другой вкладке),
            // то убивать и создавать заново
            if (request.getSession().getAttribute(USER)!=null){
                request.getSession().invalidate();
            }
            if (UserService.getInstance().login(login, password)){
                request.getSession(true).setAttribute(LOGIN,
                        UserService.getInstance().findUserByLogin(login).getLogin());
                request.getSession().setAttribute(USER_ID,
                        UserService.getInstance().findUserByLogin(login).getUserId());
                request.getSession().setAttribute(ROLE, UserService.getInstance().findUserByLogin(login).getRole());
                //page = toResponse.get(ParameterConstant.PAGE);
            } else {
                request.setAttribute(ERROR_MESSAGE, "error");
                //todo что за константа?
                page = PageConstant.LOGIN_PAGE;
            }
        } catch (ServiceException e) {
            throw new CommandException("Exception in LoginCommand.", e);
        }
        logger.info("Return to the page " + page);
        return page;
    }
}
