package by.epam.bookrating.command.impl;
import by.epam.bookrating.command.Command;
import by.epam.bookrating.command.PageConstant;
import by.epam.bookrating.command.ParameterConstant;
import by.epam.bookrating.command.CommandException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.epam.bookrating.command.ParameterConstant.*;


public class ChangeLocaleCommand implements Command {
    private ChangeLocaleCommand(){}
    private static ChangeLocaleCommand instance = new ChangeLocaleCommand();
    public static ChangeLocaleCommand getInstance() {
        return instance;
    }
    private static Logger logger = Logger.getLogger(ChangeLocaleCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String locale = request.getParameter(LOCALE).toLowerCase();
        request.getSession().setAttribute(LOCALE, locale);
        logger.info("Locale is successfully changed.");
        return PageConstant.MAIN_PAGE;
    }
}
