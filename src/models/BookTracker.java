package models;

import java.io.Serializable;

public class BookTracker implements Serializable {
    private boolean isAvailable;
    private long dateOfReturn;
    private User userThatBorrowed;
    private static final long serialVersionUID = 1L;

    public BookTracker() {
        this.isAvailable = true;
        this.dateOfReturn = 0;
        this.userThatBorrowed = null;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public long getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(long dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    public User getUserThatBorrowed() {
        return userThatBorrowed;
    }

    public void setUserThatBorrowed(User userThatBorrowed) {
        this.userThatBorrowed = userThatBorrowed;
    }

    @Override
    public String toString() {
        return "BookTracker{" +
                "isAvailable=" + isAvailable +
                ", dateOfBorrow=" + dateOfReturn +
                ", userThatBorrowed='" + userThatBorrowed + '\'' +
                '}';
    }
}