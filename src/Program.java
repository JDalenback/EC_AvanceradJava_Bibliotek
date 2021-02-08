import Utils.LibraryFileUtils;
import models.Library;
import models.Menu;
import models.User;

import java.io.Serial;
import java.io.Serializable;

public class Program implements Serializable {
    private Library library = null;
    Menu menu = new Menu();
    @Serial
    private static final long serialVersionUID = 1L;

    public void start() {
        setLibrary(LibraryFileUtils.deSerializeObject());
        library.sortByTitle();
        //library.checkIfUserNameExists(new User("Molly", "123", true));
        library.addUser();
        //Library.serializeObject(library, "src/models/books.ser");
        //menu.login();
        menu.login(library);
    }

    private void saveLibraryToFile() {
        LibraryFileUtils.serializeObject(library);
    }

    private void loadLibraryFroFile() {
        setLibrary(LibraryFileUtils.deSerializeObject());
    }

    private void setLibrary(Object object) {
        if (object != null) {
            this.library = (Library) object;
        }
        else
            this.library = Library.getLibraryInstance();
    }
}
