package by.epam.bookrating.controller.command.impl.user;
import by.epam.bookrating.controller.command.Command;
import by.epam.bookrating.controller.constant.PageConstant;
import by.epam.bookrating.controller.constant.ParameterConstant;
import by.epam.bookrating.controller.exception.CommandException;
import by.epam.bookrating.service.exception.ServiceException;
import by.epam.bookrating.service.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by anyab on 06.11.16.
 */
public class ViewFavoriteBooksCommand implements Command {
    private static ViewFavoriteBooksCommand instance = new ViewFavoriteBooksCommand();
    public static ViewFavoriteBooksCommand getInstance() {
        return instance;
    }
    private ViewFavoriteBooksCommand() {
    }

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        if (isUser(request)){
            long userId = Long.parseLong(request.getParameter(ParameterConstant.USER_ID));
            try {
                request.setAttribute(ParameterConstant.BOOK_LIST,
                        UserService.getInstance().findFavoriteBooks(userId));
                request.setAttribute(ParameterConstant.USER_ID, userId);
            } catch (ServiceException e) {
                throw new CommandException("Exception", e);
            }
            return PageConstant.FAVORITE_BOOKS;
        } else {
            return PageConstant.LOGIN_PAGE;
        }

    }
}
