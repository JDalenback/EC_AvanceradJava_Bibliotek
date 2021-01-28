import models.Librarian;
import models.Library;
import models.User;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Program p = new Program();
        p.start();
        Librarian.readFile();
    }
}
