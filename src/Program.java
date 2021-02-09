import Utils.LibraryFileUtils;
import models.Library;
import models.Menu;
import java.io.Serializable;

public class Program implements Serializable {
    private Library library = Library.instance;
    Menu menu = new Menu();
    private static final long serialVersionUID = 1L;

    public void start() {
        //library.populateMockupLibrary();
        //library.showAllUsers();

        menu.login();
    }

}
