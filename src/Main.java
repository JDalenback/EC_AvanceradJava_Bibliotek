import models.Librarian;
import models.Library;
import models.User;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Program p = new Program();
        p.start();

        Library library = new Library();
        Library.serializeObject(library, "src/models/books.ser");

        try{
            Library.readBookFromFile();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
