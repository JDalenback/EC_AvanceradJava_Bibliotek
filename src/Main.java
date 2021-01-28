import models.Book;
import models.BookTracker;
import models.Librarian;
import models.Visitor;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Program program = new Program();
        program.start();

        BookTracker trackMe= new BookTracker();

        Librarian l1 = new Librarian("Jonas", "1146");
        Visitor timmie = new Visitor("Timmie", "345");
        models.Book b1 = new Book("Lisa", "Liza", "123", "blabla", trackMe);
        models.Book b2 = new Book("LIZA", "Liza", "124", "blabla", trackMe);

    }
}
