
import models.Library;

import java.io.IOException;

public class Program {

    public void start() throws IOException, ClassNotFoundException {

        Library library = new Library();
        library.showAllBooksInLibrary();
        library.addNewBookToLibrary();
        library.showAllBooksInLibrary();
        library.removeBookFromLibrary();
        System.out.println();

       library.searchForBook("ur");
    }
}
