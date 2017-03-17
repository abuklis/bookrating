package by.epam.bookrating.controller.command;
import by.epam.bookrating.controller.command.impl.ChangeLocaleCommand;
import by.epam.bookrating.controller.command.impl.LeaveRatingCommand;
import by.epam.bookrating.controller.command.impl.book.*;
import by.epam.bookrating.controller.command.impl.comment.BanUserCommand;
import by.epam.bookrating.controller.command.impl.user.*;
import by.epam.bookrating.controller.command.impl.comment.*;

/**
 * Class-container of commands
 * return current command
 */

public enum CommandEnum {
    LOGIN(LoginCommand.getInstance()), LOGINPAGE(LoginPageCommand.getInstance()),
    LOGOUT(LogoutCommand.getInstance()), VIEWSINGLE(ViewSingleBookCommand.getInstance()),
    ADDBOOKPAGE(AddBookPageCommand.getInstance()), ADDBOOK(AddBookCommand.getInstance()),
    DELETEBOOK(DeleteBookCommand.getInstance()),VIEWALLBOOKS( ViewAllBooksCommand.getInstance()),
    EDITBOOKPAGE(EditBookPageCommand.getInstance()), EDITBOOK(EditBookCommand.getInstance()),
    REGISTERPAGE(RegistrationPageCommand.getInstance()), REGISTRATION(RegistrationCommand.getInstance()),
    LEAVECOMMENT(LeaveCommentCommand.getInstance()), LEAVERATING(LeaveRatingCommand.getInstance()),
    VIEWPROFILE(ViewProfileCommand.getInstance()), EDITPROFILEPAGE(EditProfilePageCommand.getInstance()),
    EDITPROFILE(EditProfileCommand.getInstance()), FINDBOOKSBYSINGLEGENRE(FindBooksByGenreCommand.getInstance()),
    FINDBOOKSBYAUTHOR(FindBooksByAuthorCommand.getInstance()), VIEWTODAYCOMMENTS(ViewTodayCommentsCommand.getInstance()),
    DELETECOMMENT(DeleteCommentCommand.getInstance()), VIEWALLUSERS(ViewAllUsersCommand.getInstance()),
    BANUSER(BanUserCommand.getInstance()), VIEWUSERCOMMENTS(ViewUserCommentsCommand.getInstance()),
    ADDTOFAVORITEBOOKS(AddBookToFavoriteCommand.getInstance()), ADDTOREADBOOKS(AddBookToReadCommand.getInstance()),
    VIEWFAVORITEBOOKSCOMMAND(ViewFavoriteBooksCommand.getInstance()), DELETEBOOKFROMFAVORITE(DeleteBookFromFavoriteCommand.getInstance()),
    ADDAUTHORPAGE(AddAuthorPageCommand.getInstance()), ADDAUTHOR(AddAuthorCommand.getInstance()),
    VIEWAUTHOR( ViewAuthorCommand.getInstance()), EDITAUTHORPAGE(EditAuthorPageCommand.getInstance()),
    EDITAUTHOR(EditAuthorCommand.getInstance()), DELETEBOOKFROMREAD(DeleteBookFromReadCommand.getInstance()),
    VIEWREADBOOKS(ViewReadBooksCommand.getInstance()), CHANGELOCALE(ChangeLocaleCommand.getInstance());

    Command command;
    CommandEnum(Command instance) {
        this.command = instance;
    }
    public Command getCurrentCommand() {
        return command;
    }
}