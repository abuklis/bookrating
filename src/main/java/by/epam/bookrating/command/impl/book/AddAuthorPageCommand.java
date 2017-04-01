package by.epam.bookrating.command.impl.book;
import by.epam.bookrating.command.Command;
import by.epam.bookrating.command.PageConstant;
import by.epam.bookrating.command.CommandException;

import javax.servlet.http.HttpServletRequest;


public class AddAuthorPageCommand  implements Command {
    private static AddAuthorPageCommand instance = new AddAuthorPageCommand();
    public static AddAuthorPageCommand getInstance() {
        return instance;
    }
    private AddAuthorPageCommand() {
    }

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        if (isAdmin(request)) {
            return PageConstant.ADD_AUTHOR_PAGE;
        } else {
            return PageConstant.ERROR_PAGE;
        }
    }
}
