
import models.Library;
import models.Menu;

public class Program {
    private Library library = null;
    Menu menu = new Menu();

    public void start() {
        setLibrary(Library.deSerializeObject());
        library.readInBooks();

        library.showAllBooksInLibrary();
        library.addNewBookToLibrary();
        Library.serializeObject(library, "src/models/books.ser");


        library.showAllBooksInLibrary();
        library.removeBookFromLibrary();
        System.out.println();
       // User u1 = new User("Joans", "345", true);
        library.searchForBook("ur");
        menu.loggIn();
    }

    public void setLibrary(Library object) {
        if (object != null) {
            this.library = (Library) object;
        } else {
            this.library = new Library();
        }
    }
}

