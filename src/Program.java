import Utils.LibraryFileUtils;
import models.Book;
import models.Library;
import models.User;

public class Program {
    private Library library = null;

    public void start() {
        setLibrary(Library.deSerializeObject());

        //library.populateMockupLibrary();

        //library.showStatusOfBooks();
        Library.serializeObject(library, "src/models/books.ser");
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

