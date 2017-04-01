package by.epam.bookrating.command.impl;

import by.epam.bookrating.command.Command;
import by.epam.bookrating.command.PageConstant;
import by.epam.bookrating.command.CommandException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by anyab on 29.11.2016.
 */
public class EmptyCommand implements Command {
    private EmptyCommand(){}
    private static EmptyCommand instance = new EmptyCommand();
    public static EmptyCommand getInstance(){return instance;}

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        return PageConstant.ERROR_PAGE;
    }
}
