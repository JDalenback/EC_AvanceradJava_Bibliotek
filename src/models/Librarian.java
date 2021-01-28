package models;

public class Librarian extends User {

    //private String adminID = "**";


    public Librarian(String name, String userID) {
        super(name, userID + "**");

    }

}
