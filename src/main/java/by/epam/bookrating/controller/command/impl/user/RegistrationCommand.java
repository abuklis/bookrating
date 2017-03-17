package by.epam.bookrating.controller.command.impl.user;
import by.epam.bookrating.controller.command.Command;
import by.epam.bookrating.controller.command.impl.ConverseToHashMap;
import by.epam.bookrating.controller.constant.PageConstant;
import by.epam.bookrating.controller.constant.ParameterConstant;
import by.epam.bookrating.controller.exception.CommandException;
import by.epam.bookrating.service.exception.ServiceException;
import by.epam.bookrating.service.service.UserService;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * Created by anyab on 22.09.16.
 */
public class RegistrationCommand implements Command {
    private RegistrationCommand(){}
    private static RegistrationCommand instance = new RegistrationCommand();
    public static RegistrationCommand getInstance() {
        return instance;
    }
    private static final Logger logger = Logger.getLogger(RegistrationCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(ParameterConstant.LOGIN);
        String page = PageConstant.MAIN_PAGE;
        Enumeration<String> parameters = request.getParameterNames();
        HashMap<String, String> toService = ConverseToHashMap.createParametersMap(parameters,request);
        try {
            if (UserService.getInstance().registerUser(toService)){
                request.getSession().setAttribute(ParameterConstant.ROLE, ParameterConstant.USER);
                request.getSession().setAttribute(ParameterConstant.USER,
                        UserService.getInstance().findUserByLogin(login));
                request.getSession().setAttribute(ParameterConstant.USER_ID,
                        UserService.getInstance().findUserByLogin(login).getUserId());
            } else {
                request.setAttribute(ParameterConstant.ERROR_MESSAGE, "error");
                page = PageConstant.REGISTRATION_PAGE;
            }
        } catch (ServiceException e) {
            throw new CommandException("Exception in RegistrationCommand", e);
        }
        return page;
    }
}
