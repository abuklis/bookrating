package by.epam.bookrating.controller.command.impl.user;
import by.epam.bookrating.controller.command.Command;
import by.epam.bookrating.controller.command.impl.ConverseToHashMap;
import by.epam.bookrating.controller.constant.PageConstant;
import by.epam.bookrating.controller.constant.ParameterConstant;
import by.epam.bookrating.controller.exception.CommandException;
import by.epam.bookrating.service.exception.ServiceException;
import by.epam.bookrating.service.service.CommentService;
import by.epam.bookrating.service.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by anyab on 22.10.16.
 */
public class EditProfileCommand implements Command {
    private static EditProfileCommand instance = new EditProfileCommand();
    public static EditProfileCommand getInstance() {
        return instance;
    }
    private EditProfileCommand() {}
    private static final Logger logger = Logger.getLogger(EditProfileCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        if (isUser(request)){
            Enumeration<String> parameters = request.getParameterNames();
            long userId = Long.parseLong(request.getSession().getAttribute(ParameterConstant.USER_ID).toString());
            Map<String, String> toService = ConverseToHashMap.createParametersMap(parameters,request);
            toService.put(ParameterConstant.USER_ID, String.valueOf(userId));
            String absoluteDiskPath = request.getServletContext().getRealPath("/");
            try {
                Part imgPart = request.getPart(ParameterConstant.NEW_IMAGE_URL);
                if (imgPart!=null){
                    UserService.getInstance().editAvatar(imgPart, absoluteDiskPath,
                            toService.get(ParameterConstant.OLD_IMAGE_URL), userId);
                }
            } catch (IOException | ServletException | ServiceException e) {
                logger.info("Exception during getting current avatar of the user.");
            }
            try {
                request.getSession().setAttribute(ParameterConstant.USER, UserService.getInstance().editProfile(toService));
                request.setAttribute(ParameterConstant.FAVORITE_BOOKS,
                        UserService.getInstance().findFavoriteBooks(userId));
                request.setAttribute(ParameterConstant.READ_BOOKS, UserService.getInstance().findReadBooks(userId));
                request.setAttribute(ParameterConstant.COMMENTS,
                        CommentService.getInstance().findCommentsByUserId(userId));
            } catch (ServiceException e) {
                logger.info("Exception in EditProfileCommand");
            }
            return PageConstant.VIEW_PROFILE_PAGE;
        } else {
            return PageConstant.ERROR_PAGE;
        }
    }
}
