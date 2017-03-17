package by.epam.bookrating.dao.entity;
import java.util.List;

/**
 * Created by anyab on 10.09.16.
 */
public class Book {
    private long bookId;
    private List<Author> authors;
    private String title;
    private String description;
    private int publishingYear;
    private List<Genre> genres;
    private String imageUrl;

    public Book() {
        super();
    }

    public Book(long bookId, String title, String description, int publishingYear,
                List<Genre> genres, String imageUrl) {
        this.bookId = bookId;
        this.title = title;
        this.description = description;
        this.publishingYear = publishingYear;
        this.genres = genres;
        this.imageUrl = imageUrl;
    }

    public Book(long bookId, String title, String description, int publishingYear,
                String imageUrl) {
        this.bookId = bookId;
        this.title = title;
        this.description = description;
        this.publishingYear = publishingYear;
        this.imageUrl = imageUrl;
    }

    public Book(List<Author> authors, long bookId, String title, String description,
                int publishingYear, List<Genre> genres) {
        this.authors = authors;
        this.bookId = bookId;
        this.title = title;
        this.description = description;
        this.publishingYear = publishingYear;
        this.genres = genres;
    }

    public Book(List<Author> author, String title, String description, int publishingYear,
                List<Genre> genres, String imageUrl) {
        this.authors = author;
        this.title = title;
        this.description = description;
        this.publishingYear = publishingYear;
        this.genres = genres;
        this.imageUrl = imageUrl;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> author) {
        this.authors = author;
    }

    public void setPublishingYear(int publishingYear) {
        this.publishingYear = publishingYear;
    }

    public int getPublishingYear() {
        return publishingYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", authors=" + authors +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", publishingYear=" + publishingYear +
                ", genres=" + genres +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
