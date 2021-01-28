package models;

import java.io.Serializable;

public class Book implements Serializable {
    private String title;
    private String author;
    private String isbn;
    private String description;
    private BookTracker bookTracker;

    public Book(String title, String author, String isbn, String description, BookTracker bookTracker) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.description = description;
        this.bookTracker = bookTracker;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BookTracker getBookTracker() {
        return bookTracker;
    }

    public void setBookTracker(BookTracker bookTracker) {
        this.bookTracker = bookTracker;
    }

    @Override
    public String toString() {
        return String.format("Title: %s. Author: %s. ISBN: %s", title, author, isbn);
    }
}
