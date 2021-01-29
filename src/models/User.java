package models;

import java.io.Serializable;
import java.util.HashMap;

public class User implements Serializable {

    private String name;
    private String userID;
    private boolean admin;
    private HashMap<String, Book> myBookList = new HashMap<>();

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

    public HashMap<String, Book> getMyBookList() {
        return myBookList;
    }

    public void setMyBookList(HashMap<String, Book> myBookList) {
        this.myBookList = myBookList;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", userID='" + userID + '\'' +
                ", admin=" + admin +
                ", myBookList=" + myBookList +
                '}';
    }
}