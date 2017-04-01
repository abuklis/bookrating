package by.epam.bookrating.command.impl.comment;
import by.epam.bookrating.command.Command;
import by.epam.bookrating.command.CommandException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
/**
 * Created by anyab on 26.09.16.
 */
public class ViewAllCommentsCommand implements Command {
    private ViewAllCommentsCommand(){}
    private static ViewAllCommentsCommand instance = new ViewAllCommentsCommand();
    public static ViewAllCommentsCommand getInstance() {
        return instance;
    }
    private static Logger logger = Logger.getLogger(ViewAllCommentsCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        return null;
    }
}
