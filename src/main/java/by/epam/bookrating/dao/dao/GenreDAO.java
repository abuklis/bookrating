package by.epam.bookrating.dao.dao;

import by.epam.bookrating.dao.entity.Genre;
import by.epam.bookrating.dao.exception.DAOException;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by anyab on 16.02.2017.
 */
public interface GenreDAO {
    List<Genre> findAllGenres() throws DAOException;

    Genre findGenreById(int genreId) throws DAOException;

    List<Genre> findGenresByBookId(long bookId) throws DAOException;

    void deleteAllGenresForBook(long bookId) throws DAOException;
}
