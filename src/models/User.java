package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    private String name;
    private String userID;
    private boolean admin;
    private List<Book> myBooks = new ArrayList<>();


    public User(String name, String password, boolean admin) {
        this.name = name;
        this.userID = password;
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public void printMyBooks() {
        System.out.println("\t\t----------------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < getMyBooks().size(); i++) {
            long temp = getMyBooks().get(i).getBookTracker().getDateOfReturn();
            System.out.print("\t\t" + (i + 1) + ".\t" + getMyBooks().get(i).getTitle() + ", written by " + getMyBooks().get(i).getAuthor());
           Library.instance.lendingStatusDate(temp);
        }
        System.out.println("\t\t----------------------------------------------------------------------------------------------------------------------");
    }

    public void printThisUser(User user) {
        if (this != null) {
            System.out.println("\t\t----------------------------------------------------------------------------------------------------------------------");
            System.out.println("\t\t" + user.getName()+".\n\t\tAdmin: "+user.isAdmin()+".\n\n\t\tBooks:");
            user.getMyBooks().forEach(book -> System.out.println("\t\t"+book));
            System.out.println("\t\t----------------------------------------------------------------------------------------------------------------------");
        } else System.out.println("\t\tDoesn't exist, please try again. ");
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

    public int numberOfBorrowedBooks(){
        return myBooks.size();
    }

    public Long numberOfLateBooks(){
        return  myBooks
                .stream()
                .filter(userBook -> userBook.getBookTracker().getDateOfReturn() < System.currentTimeMillis())
                .count();
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
        String userInfo =  "Name = " + name + "\n" +
                "\t\tUserID = " + userID + "\n" +
                "\t\tAdmin = " + admin + "\n" +
                "\t\tMy Books = " + myBooks;

        return userInfo;
    }
}