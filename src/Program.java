import models.Library;

public class Program {

    public void start() {

        Library library = new Library();
        library.showAllBooksInLibrary();
        library.addNewBookToLibrary();
        library.showAllBooksInLibrary();
        library.removeBookFromLibrary();
        System.out.println();

       library.searchForBook("ur");
    }


}
