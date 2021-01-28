package models;

public class Librarian extends User {

    private String adminID = "**";


    public Librarian(String name, String userID) {
        super(name, userID);

    }

    @Override
    public String getUserID() {
        return super.getUserID()+adminID;
    }

    @Override
    public String toString() {
        return "Librarian{" +
                "adminID='" + adminID + '\'' +
                '}';
    }
}
