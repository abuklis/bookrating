package by.epam.bookrating.dao;

import by.epam.bookrating.entity.Genre;


import java.util.List;

/**
 * Created by anyab on 16.02.2017.
 */
public interface GenreDAO {
    List<Genre> findAllGenres() throws DAOException;

    Genre findGenreById(int genreId) throws DAOException;

    List<Genre> findGenresByBookId(long bookId) throws DAOException;
}
