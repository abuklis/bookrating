package by.epam.bookrating.entity;
import java.sql.Date;

/**
 * Created by anyab on 25.09.16.
 */
public class Comment {
    private long commentId;
    private long bookId;
    private long userId;
    private Date commentDate;
    private String commentText;

    public Comment() {
        super();
    }

    public Comment(long commentId, long bookId, long userId, Date commentDate, String commentText) {
        this.commentId = commentId;
        this.bookId = bookId;
        this.userId = userId;
        this.commentDate = commentDate;
        this.commentText = commentText;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "bookId=" + bookId +
                ", commentId=" + commentId +
                ", commentDate=" + commentDate +
                ", commentText='" + commentText + '\'' +
                '}';
    }
}
