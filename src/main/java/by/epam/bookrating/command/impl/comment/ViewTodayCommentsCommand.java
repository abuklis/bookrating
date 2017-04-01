package by.epam.bookrating.command.impl.comment;
import by.epam.bookrating.command.Command;
import by.epam.bookrating.command.PageConstant;
import by.epam.bookrating.command.ParameterConstant;
import by.epam.bookrating.command.CommandException;
import by.epam.bookrating.service.ServiceException;
import by.epam.bookrating.service.CommentService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.epam.bookrating.command.ParameterConstant.*;

/**
 * Created by anyab on 27.10.16.
 */
public class ViewTodayCommentsCommand implements Command {
    private ViewTodayCommentsCommand(){}
    private static ViewTodayCommentsCommand instance = new ViewTodayCommentsCommand();
    public static ViewTodayCommentsCommand getInstance() {
        return instance;
    }
    private static Logger logger = Logger.getLogger(ViewTodayCommentsCommand.class);


    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        if (isAdmin(request)){
            long time = System.currentTimeMillis();
            java.sql.Date date = new java.sql.Date(time);
            logger.info("Current date: "+ date );
            try{
                request.setAttribute(COMMENTS, CommentService.getInstance().viewTodayComments(date));
            } catch (ServiceException e){
                throw new CommandException("Exception in ViewTodayCommand.", e);
            }
            return PageConstant.VIEW_TODAY_COMMENTS_PAGE;
        } else {
            return PageConstant.ERROR_PAGE;
        }
    }
}
