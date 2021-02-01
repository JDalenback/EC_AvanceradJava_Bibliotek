import Utils.LibraryFileUtils;
import models.Book;
import models.Library;
import models.Menu;

import java.io.Serializable;

public class Program implements Serializable{
    private Library library = null;
    Menu menu = new Menu();

    public void start() {
        setLibrary(Library.deSerializeObject());

        library.watch("insert", event ->
                library.serializeObject(library, "src/models/books.ser"));

        library.watch("delete", event  ->
                library.serializeObject(library, "src/models/books.ser"));

        //library.populateMockupLibrary();

        library.showAllBooksInLibrary();
        library.removeBookFromLibrary();
        System.out.println();
        library.showAllBooksInLibrary();

        
        //Library.serializeObject(library, "src/models/books.ser");
       // menu.login(library);
    }

    private void saveLibraryToFile() {
        LibraryFileUtils.serializeObject(library);
    }

    private void loadLibraryFroFile() {
        setLibrary(LibraryFileUtils.deSerializeObject());
    }

    private void setLibrary(Object object) {
        if (object != null)
            this.library = (Library) object;
        else
            this.library = new Library();
    }
}
