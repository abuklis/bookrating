package by.epam.bookrating.dao;

import by.epam.bookrating.entity.User;

import java.util.List;

/**
 * Created by anyab on 09.02.2017.
 */
public interface UserDAO {
    void addUser(String login, String password, String name) throws DAOException;

    User findUserByUserId(long userId) throws DAOException;

    boolean isLoginExists(String login) throws DAOException;

    void updateProfileInformation(String name, int age, String info, long userId) throws DAOException;

    void updateAvatar(long userId, String imageUrl) throws DAOException;

    void banUser(long userId) throws DAOException;

    List<User> findAllUsers() throws DAOException;

    User findUserByLogin(String login) throws DAOException;
}
