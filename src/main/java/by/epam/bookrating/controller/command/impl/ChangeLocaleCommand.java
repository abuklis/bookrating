package by.epam.bookrating.controller.command.impl;
import by.epam.bookrating.controller.command.Command;
import by.epam.bookrating.controller.constant.PageConstant;
import by.epam.bookrating.controller.constant.ParameterConstant;
import by.epam.bookrating.controller.exception.CommandException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;


public class ChangeLocaleCommand implements Command {
    private ChangeLocaleCommand(){}
    private static ChangeLocaleCommand instance = new ChangeLocaleCommand();
    public static ChangeLocaleCommand getInstance() {
        return instance;
    }
    private static final Logger logger = Logger.getLogger(ChangeLocaleCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String locale = request.getParameter(ParameterConstant.LOCALE).toLowerCase();
        request.getSession().setAttribute(ParameterConstant.LOCALE, locale);
        logger.info("Locale is successfully changed.");
        return PageConstant.MAIN_PAGE;
    }
}
