package by.epam.bookrating.service.service;
import by.epam.bookrating.dao.dao.UserDAO;
import by.epam.bookrating.dao.dao.impl.MySQLBookDAO;
import by.epam.bookrating.dao.dao.impl.MySQLUserDAO;
import by.epam.bookrating.dao.entity.Book;
import by.epam.bookrating.dao.entity.User;
import by.epam.bookrating.dao.exception.DAOException;
import by.epam.bookrating.service.exception.ServiceException;
import by.epam.bookrating.service.logic.HashLogic;
import by.epam.bookrating.service.logic.ImageLogic;
import org.apache.log4j.Logger;

import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by anyab on 22.10.16.
 */
public class UserService {
    private UserDAO userDao;
    private static UserService instance = new UserService();
    public static UserService getInstance() {
        return instance;
    }
    private UserService() {
        userDao = MySQLUserDAO.getInstance();
    }
    private static final String LOGIN = "login";
    private static final String USER_ID = "userId";
    private static final String AGE = "age";
    private static final String INFO = "info";
    private static final String NAME = "name";
    private static final String PASSWORD = "password";
    private static final String IMG_DIR_PATH = "img/";

    private static final Logger logger = Logger.getLogger(UserService.class);

    public User editProfile(Map<String, String> parameters) throws ServiceException {
        User currentUser;
        try {
            logger.info("Trying to update profile item with login = " + parameters.get(LOGIN));
            userDao.updateProfileInformation( parameters.get(NAME),
                    Integer.parseInt(parameters.get(AGE)), parameters.get(INFO),
                    Long.parseLong(parameters.get(USER_ID)));
            logger.info("Profile " + parameters.get(LOGIN) + " is successfully updated.");
            currentUser = userDao.findUserByUserId(Long.parseLong(parameters.get(USER_ID)));
        } catch (DAOException e){
            throw new ServiceException("Service Exception in EditBookService", e);
        }
        return currentUser;
    }

    public void editAvatar(Part imagePart, String absoluteDiskPath, String oldImage, long userId)
            throws ServiceException {
        String fileName = ImageLogic.getFileName(imagePart);
        String realImageUrl= IMG_DIR_PATH + fileName;
        String imageURL;

        imageURL = ImageLogic.addImage(imagePart, absoluteDiskPath);
        if (!realImageUrl.equals(IMG_DIR_PATH) && !realImageUrl.equals(oldImage)){
            try {
                userDao.updateAvatar(userId, imageURL);
            } catch (DAOException e) {
                throw new ServiceException("Exception in editAvatar method", e);
            }
        } else {
            logger.info("Picture wasn't change. Do nothing.");
        }
        logger.info("New avatar url " + imageURL);
    }

    public boolean login(String login, String password) throws ServiceException {
        try {
            String hashPassword = HashLogic.hashPassword(password);
            if (userDao.isLoginExists(login)) {
                User user = userDao.findUserByLogin(login);
                if (hashPassword.equals(user.getPassword())) {
                    return true;
                }
            }
        } catch (DAOException e) {
            throw new ServiceException("Exception in LoginService.", e);
        }
        return false;
    }

//    private String getPagePath(String page){
//        int endOfPagePath = page.indexOf(PAGE_DIR_PATH);
//        return page.substring(endOfPagePath);
//    }

    public boolean registerUser(Map<String, String> parameters) throws ServiceException {
        String login = parameters.get(LOGIN);
        try{
            if (!userDao.isLoginExists(login)){
                String hashPassword = HashLogic.hashPassword(parameters.get(PASSWORD));
                userDao.addUser(login, hashPassword, parameters.get(NAME));
                logger.info("User with login "+ login + " successfully registered.");
                return true;
            } else {
                logger.info("User with such login exists.");
            }
        } catch (DAOException e){
            throw new ServiceException("Exception in occurred during registration", e);
        }
        return false;
    }

    public User findUserById(long userId) throws ServiceException {
        User currentUser;
        try{
            currentUser = userDao.findUserByUserId(userId);
            logger.info("Sending user with id = "+ userId + "to the page.");
        } catch ( DAOException e){
            throw new ServiceException("Exception in ViewSingleService", e);
        }
        return currentUser;
    }

    public User findUserByLogin(String login) throws ServiceException {
        User currentUser;
        try{
            currentUser = userDao.findUserByLogin(login);
            logger.info("Sending user with id = "+ login + "to the page.");
        } catch ( DAOException e){
            throw new ServiceException("Exception in ViewSingleService", e);
        }
        return currentUser;
    }

    public List<User> viewAllUsers() throws ServiceException {
        List<User> users;
        try{
            users = userDao.findAllUsers();
            logger.info("Sending " + users.size() + "users array to a controller.");
        } catch (DAOException e){
            throw new ServiceException("Service Exception is occurred in this method.", e);
        }
        return users;
    }

    public void banUser(long userId) throws ServiceException {
        try {
            userDao.banUser(userId);
        } catch (DAOException e) {
            throw new ServiceException("Service Exception is occurred in this method.", e);
        }
    }

    public User findUserByUserId(long userId) throws ServiceException {
        User user;
        try {
             user = userDao.findUserByUserId(userId);
        } catch (DAOException e) {
            throw new ServiceException("Service Exception is occurred in this method.", e);
        }
        return user;
    }

    public List<Book> findFavoriteBooks(long userId) throws ServiceException{
        try {
            return MySQLBookDAO.getInstance().findFavoriteBooks(userId);
        } catch (DAOException e) {
            throw new ServiceException("Service Exception is occurred in ths method.", e);
        }
    }

    public List<Book> findReadBooks(long userId) throws ServiceException {
        try {
            return MySQLBookDAO.getInstance().findReadBooks(userId);
        } catch (DAOException e) {
            throw new ServiceException("Service Exception is occurred in ths method.", e);
        }
    }

    public void deleteBookFromFavorite(long userId, long bookId) throws ServiceException{
        try {
            MySQLBookDAO.getInstance().deleteBookFromFavorite(userId, bookId);
        } catch (DAOException e) {
            throw new ServiceException("Service Exception is occurred in deleteBookFromFavorite method.", e);
        }
    }

    public void deleteBookFromRead(long userId, long bookId) throws ServiceException{
        try {
            MySQLBookDAO.getInstance().deleteBookFromRead(userId, bookId);
        } catch (DAOException e) {
            throw new ServiceException("Service Exception is occurred in deleteBookFromRead method.", e);
        }
    }

    public boolean addBookToFavorite(long userId, long bookId) throws ServiceException{
        try {
            return MySQLBookDAO.getInstance().addToFavorite(userId, bookId);
        } catch (DAOException e) {
            throw new ServiceException("Service Exception is occurred in addBookToFavorite method.", e);
        }
    }

    public boolean addBookToRead(long userId, long bookId) throws ServiceException{
        try {
            return MySQLBookDAO.getInstance().addToRead(userId, bookId);
        } catch (DAOException e) {
            throw new ServiceException("Service Exception is occurred in addBookToFavorite method.", e);
        }
    }

    public boolean isBookInFavorite(long userId, long bookId) throws ServiceException{
        try {
            return MySQLBookDAO.getInstance().isBookInFavorite(userId, bookId);
        } catch (DAOException e) {
            throw new ServiceException("Service Exception is occurred in isBookInFavorite method.", e);
        }
    }

    public boolean isBookInRead(long userId, long bookId) throws ServiceException{
        try {
            return MySQLBookDAO.getInstance().isBookInRead(userId, bookId);
        } catch (DAOException e) {
            throw new ServiceException("Service Exception is occurred in isBookInRead method.", e);
        }
    }
}
