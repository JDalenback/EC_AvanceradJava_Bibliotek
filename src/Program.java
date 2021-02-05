import Utils.LibraryFileUtils;
import models.Library;
import models.Menu;
import models.User;

import java.io.Serializable;

public class Program implements Serializable {
    private Library library = null;
    Menu menu = new Menu();

    public void start() {
        setLibrary(Library.deSerializeObject());
        setLibrary(LibraryFileUtils.deSerializeObject());
<<<<<<< HEAD
        library.showAllUsers();
=======

        //library.populateMockupLibrary();
        library.populateMockupLibrary();
    //    library.checkIfUserNameExists(new User("Molly", "12345", false));
>>>>>>> a6480b3d73556c2a6e72a4f58b2eac3a00c9dae1
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
