package by.epam.bookrating.command;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface {@code ICommand} is the interface that consist of method dealing with
 * HttpRequest and HttpResponse
 * @author Anna Buklis
 */

public interface Command {
    String execute(HttpServletRequest request) throws CommandException;

    default boolean isAdmin(HttpServletRequest request) {
        return RoleEnum.ADMIN.name().toLowerCase().equals(request.getSession().getAttribute(ParameterConstant.ROLE));
    }

    default boolean isUser(HttpServletRequest request) {
        return RoleEnum.USER.name().toLowerCase().equals(request.getSession().getAttribute(ParameterConstant.ROLE))||
                RoleEnum.BANNED.name().toLowerCase().equals(request.getSession().getAttribute(ParameterConstant.ROLE));
    }
}