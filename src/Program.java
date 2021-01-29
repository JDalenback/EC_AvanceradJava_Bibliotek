import models.Library;
import models.User;

public class Program {

    public void start() {

        Library library = new Library();
        library.showAllBooksInLibrary();
        library.addNewBookToLibrary();
        library.showAllBooksInLibrary();
        library.removeBookFromLibrary();
        System.out.println();
        User u1 = new User("Joans", "345", true);
        library.searchForBook("ur");
    }

}