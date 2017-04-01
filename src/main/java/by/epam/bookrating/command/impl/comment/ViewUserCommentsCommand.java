package by.epam.bookrating.command.impl.comment;
import by.epam.bookrating.command.Command;
import by.epam.bookrating.command.PageConstant;
import by.epam.bookrating.command.ParameterConstant;
import by.epam.bookrating.command.CommandException;
import by.epam.bookrating.service.ServiceException;
import by.epam.bookrating.service.CommentService;

import javax.servlet.http.HttpServletRequest;

import static by.epam.bookrating.command.ParameterConstant.*;

/**
 * Created by anyab on 29.10.16.
 */
public class ViewUserCommentsCommand implements Command {
    private static ViewUserCommentsCommand instance = new ViewUserCommentsCommand();
    public static ViewUserCommentsCommand getInstance() {
        return instance;
    }
    private ViewUserCommentsCommand() {}

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            long userId = Long.parseLong(request.getParameter(USER_ID));
            request.setAttribute(COMMENTS,CommentService.getInstance().findCommentsByUserId(userId));
            request.setAttribute(USER_ID, userId);
        } catch (ServiceException e) {
            throw new CommandException("Exception is occurred here.", e);
        }
        return PageConstant.USER_COMMENTS;
    }
}
