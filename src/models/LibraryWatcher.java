package models;

import java.io.Serializable;

@FunctionalInterface
public interface LibraryWatcher extends Serializable {

    void handle(LibraryEvent libraryEvent);
}
