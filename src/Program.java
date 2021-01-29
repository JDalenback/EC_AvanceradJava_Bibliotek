import Utils.LibraryFileUtils;
import models.Library;

public class Program {
    Library library = null;

    public void start() {

    loadLibraryFroFile();
    //library.addNewBookToLibrary();
    saveLibraryToFile();
    library.showAllBooksInLibrary();

    }

    private void saveLibraryToFile(){
        LibraryFileUtils.serializeObject(library);
    }

    private void loadLibraryFroFile(){
        setLibrary(LibraryFileUtils.deSerializeObject());
    }

    private void setLibrary(Object object){
        if(object != null)
            this.library = (Library) object;
        else
            this.library = new Library();
    }
}
