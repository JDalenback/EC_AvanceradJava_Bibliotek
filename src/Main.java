import models.Librarian;
import models.Library;

import models.Book;
import models.BookTracker;
import models.Visitor;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Program p = new Program();
        p.start();



        BookTracker trackMe= new BookTracker();

        Librarian l1 = new Librarian("Jonas", "1146");
        Visitor timmie = new Visitor("Timmie", "345");
        models.Book b1 = new Book("Lisa", "Liza", "123", "blabla", trackMe);
        models.Book b2 = new Book("LIZA", "Liza", "124", "blabla", trackMe);

        Library library = new Library();


        try{
            Library.readBookFromFile();
        }catch (Exception e){
            e.printStackTrace();
        }

        Library.serializeObject(library, "src/models/books.ser");

    }
}
