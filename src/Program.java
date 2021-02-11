import Utils.LibraryFileUtils;
import models.Library;
import models.Menu;
import java.io.Serializable;

public class Program implements Serializable {
    private Library library = Library.instance;
    Menu menu = new Menu();

    public void start() {
        //library.populateMockupLibrary();

        //library.sortByTitle();

        menu.login();
    }
}