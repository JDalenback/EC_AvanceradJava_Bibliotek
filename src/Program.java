
import models.Library;

import java.io.IOException;

public class Program {
    private Library library = null;

    public void start() {



        setLibrary(Library.deSerializeObject());
        library.readInBooks();

        library.showAllBooksInLibrary();
        library.addNewBookToLibrary();
        Library.serializeObject(library, "src/models/books.ser");


        //library.searchForBook("ur");
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
