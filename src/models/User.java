package models;

import java.util.HashMap;

abstract class User {

    private String name;
    private String userID;
    private HashMap<String, Book> myBookList = new HashMap<>();


    public User(String name, String userID) {
        this.name = name;
        this.userID = userID;

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
                ", myBookList=" + myBookList +
                '}';
    }


}