import models.Library;

public class Program {

    public void start() {
        Library library = new Library();
       library.showAllBooksInLibrary();
        System.out.println();
       library.searchForBook("ur");
    }

}
