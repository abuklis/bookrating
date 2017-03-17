package by.epam.bookrating.controller.command.impl.user;
import by.epam.bookrating.controller.command.Command;
import by.epam.bookrating.controller.constant.PageConstant;
import by.epam.bookrating.controller.constant.ParameterConstant;
import by.epam.bookrating.controller.exception.CommandException;
import by.epam.bookrating.service.exception.ServiceException;
import by.epam.bookrating.service.service.CommentService;
import by.epam.bookrating.service.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
/**
 * Created by anyab on 21.10.16.
 */
public class ViewProfileCommand implements Command {
    private ViewProfileCommand(){}
    private static ViewProfileCommand instance = new ViewProfileCommand();
    public static ViewProfileCommand getInstance() {
        return instance;
    }
    private static final Logger logger = Logger.getLogger(ViewProfileCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(ParameterConstant.LOGIN);
        long userId = Long.parseLong(request.getParameter(ParameterConstant.USER_ID));
        logger.info("Command received login = " + login);
        try {
            request.setAttribute(ParameterConstant.USER, UserService.getInstance().findUserByUserId(userId));
            request.setAttribute(ParameterConstant.FAVORITE_BOOKS, UserService.getInstance().findFavoriteBooks(userId));
            request.setAttribute(ParameterConstant.READ_BOOKS, UserService.getInstance().findReadBooks(userId));
            request.setAttribute(ParameterConstant.COMMENTS, CommentService.getInstance().findCommentsByUserId(userId));
        } catch (ServiceException e) {
            throw new CommandException("Exception in this command.", e);
        }
        return PageConstant.VIEW_PROFILE_PAGE;
    }
}
