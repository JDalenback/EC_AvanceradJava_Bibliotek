import Utils.LibraryFileUtils;
import models.Book;
import models.Library;
import models.Menu;
import models.User;


public class Program {
    private Library library = null;
    Menu menu = new Menu();

    public void start() {
        setLibrary(Library.deSerializeObject());

        //library.populateMockupLibrary();

        Library.serializeObject(library, "src/models/books.ser");
        menu.login();
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
