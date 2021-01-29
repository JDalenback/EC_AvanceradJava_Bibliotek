
import models.Library;
import models.User;

import java.io.IOException;

public class Program {
    private Library library = null;

    public void start() {
        setLibrary(Library.deSerializeObject());
        library.readInBooks();

        library.showAllBooksInLibrary();
        library.addNewBookToLibrary();
        Library.serializeObject(library, "src/models/books.ser");


        library.showAllBooksInLibrary();
        library.removeBookFromLibrary();
        System.out.println();
        User u1 = new User("Joans", "345", true);
        library.searchForBook("ur");
    }




    public void setLibrary(Library object) {
        if(object != null){
            this.library = (Library) object;
        }
        else{
            this.library = new Library();
        }
    }
}

