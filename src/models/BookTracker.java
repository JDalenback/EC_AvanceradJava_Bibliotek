package models;

import java.io.Serializable;

public class BookTracker implements Serializable {
    private boolean isAvailable;
    private int dateOfBorrow;
    private String userThatBorrowed;

    public BookTracker() {
        this.isAvailable = true;
        this.dateOfBorrow = 0;
        this.userThatBorrowed = null;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getDateOfBorrow() {
        return dateOfBorrow;
    }

    public void setDateOfBorrow(int dateOfBorrow) {
        this.dateOfBorrow = dateOfBorrow;
    }

    public String getUserThatBorrowed() {
        return userThatBorrowed;
    }

    public void setUserThatBorrowed(String userThatBorrowed) {
        this.userThatBorrowed = userThatBorrowed;
    }

    @Override
    public String toString() {
        return "BookTracker{" +
                "isAvailable=" + isAvailable +
                ", dateOfBorrow=" + dateOfBorrow +
                ", userThatBorrowed='" + userThatBorrowed + '\'' +
                '}';
    }
}
