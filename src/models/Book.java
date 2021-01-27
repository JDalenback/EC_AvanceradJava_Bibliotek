package models;

import java.io.Serializable;

public class Book implements Serializable {
    private String bookTitle;
    private String author;
    private String isbn;
    private String description;
    private BookTracker bookTracker;

    public Book(String bookTitle, String author, String isbn, String description, BookTracker bookTracker) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.isbn = isbn;
        this.description = description;
        this.bookTracker = bookTracker;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
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
        return "Book{" +
                "bookTitle='" + bookTitle + '\'' +
                ", author='" + author + '\'' +
                ", isbn=" + isbn +
                ", description='" + description + '\'' +
                ", bookTracker=" + bookTracker +
                '}';
    }
}
