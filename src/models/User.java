package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    private String name;
    private String userID;
    private boolean admin;
    private List<Book> myBooks = new ArrayList<>();

    public User(String name, String userID, boolean admin) {
        this.name = name;
        this.userID = userID;
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<Book> getMyBooks() {
        return myBooks;
    }

    public void addBookToMyBooks(Book book) {
        this.myBooks.add(book);
    }

    public void removeBookFromMyBooks(Book book) {
        myBooks.remove(book);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", userID='" + userID + '\'' +
                ", admin=" + admin +
                ", myBooks=" + myBooks +
                '}';
    }
}