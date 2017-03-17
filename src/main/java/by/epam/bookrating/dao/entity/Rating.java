package by.epam.bookrating.dao.entity;


public class Rating {
    private long ratingId;
    private long userId;
    private long bookId;
    private int rating;

    public Rating() {
        super();
    }

    public Rating(long userId, long bookId, int star) {
        this.userId = userId;
        this.bookId = bookId;
        this.rating = star;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public long getRatingId() {
        return ratingId;
    }

    public void setRatingId(long ratingId) {
        this.ratingId = ratingId;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "ratingId='" + ratingId + '\'' +
                "userId='" + userId + '\'' +
                ", bookId=" + bookId +
                ", rating=" + rating +
                '}';
    }
}
