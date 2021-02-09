import Utils.LibraryFileUtils;
import models.Library;
import models.Menu;
import java.io.Serializable;

public class Program implements Serializable {
    private Library library = null;
    Menu menu = new Menu();

    public void start() {
        setLibrary(LibraryFileUtils.deSerializeObject());
       //library.populateMockupLibrary();
        //library.showAllUsers();
        menu.login(library);
    }

    private void saveLibraryToFile() {
        LibraryFileUtils.serializeObject(library);
    }


    private void setLibrary(Object object) {
        if (object != null) {
            this.library = (Library) object;
        }
        else
            this.library = Library.getLibraryInstance();
    }
}
