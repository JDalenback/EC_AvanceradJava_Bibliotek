package models;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Menu implements Serializable {
    private static final long serialVersionUID = 1L;

    public void login() {
        Library library = Library.instance;
        Scanner scanner = new Scanner(System.in);
        String userName;
        String password;
        User user;
        boolean isRunning = true;

        while (isRunning) {
            System.out.print("\nWelcome to the library login page!\n\nType quit to end\nUsername: ");
            userName = scanner.nextLine();
            if (userName.equalsIgnoreCase("quit")) {
                break;
            } else {
                System.out.print("Password: ");
                password = scanner.nextLine();

                List<User> matchingUser = getUserThatMatchNameAndPassword
                        (library.getUsers(), userName, password);

                if (matchingUser.size() > 0) {
                    user = matchingUser.get(0);
                    if (checkUserType(matchingUser)) {
                        librarianMenu(library, user);
                    } else {
                        lenderMenu(library, user);
                    }
                } else System.out.println("Invalid name or password!");
            }
        }
    }

    private List<User> getUserThatMatchNameAndPassword(List<User> userList, String name, String password) {
        return userList.stream()
                .filter(s -> s.getName().equals(name) && s.getUserID().equals(password))
                .collect(Collectors.toList());
    }

    private boolean checkUserType(List<User> list) {
        return list.get(0).isAdmin();
    }


    private void librarianMenu(Library library, User user) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        String chose;
        System.out.printf("\nWelcome %s!", user.getName());

        while (isRunning) {
            System.out.println("\nMake one choice:");
            System.out.println("1. See available books");
            System.out.println("2. See all lent books");
            System.out.println("3. See all late returns");
            System.out.println("4. Lend a book");
            System.out.println("5. Return book");
            System.out.println("6. Add a new book to the library.");
            System.out.println("7. Remove book from library.");
            System.out.println("8. See all lenders.");
            System.out.println("9. See lender by name.");
            System.out.println("10. Add user to library");
            System.out.println("11. Remove user from library");
            System.out.println("12. See all books in library");
            System.out.println("15. Logg out");
            chose = scanner.nextLine();
            switch (chose) {
                case "1":
                    library.printoutTitle("Available books:");
                    library.showAvailableBooksInLibrary();
                    library.createReadingPauseForUser();
                    break;
                case "2":
                    library.printoutTitle("Lent books:");
                    library.showAllLentBooksInLibrary();
                    library.createReadingPauseForUser();
                    break;
                case "3":
                    library.printoutTitle("Late books:");
                    library.showAllLateBooks();
                    library.createReadingPauseForUser();
                    break;
                case "4":
                    lendABook(library, user);
                    break;
                case "5":
                    returnABook(library, user);
                    break;
                case "6":
                    library.addNewBookToLibrary();
                    library.createReadingPauseForUser();
                    break;
                case "7":
                    library.removeBookFromLibrary();
                    library.createReadingPauseForUser();
                    break;
                case "8":
                    library.getAllNoneAdminUsers();
                    library.createReadingPauseForUser();
                    break;
                case "9":
                    user.printThisUser(library.getSpecificUser(library.getInputFromUser("Name: ")));
                    library.createReadingPauseForUser();
                    break;
                case "10":
                    library.addUser();
                    library.createReadingPauseForUser();
                    break;
                case "11":
                    String userName = library.getInputFromUser("Name of user to be removed: ");
                    library.removeUser(library.getSpecificUser(userName));
                    library.createReadingPauseForUser();
                    break;
                case "12":
                    library.printAllBooksInLibrary();
                    library.createReadingPauseForUser();
                    break;
                case "15":
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid selection!");
            }
        }
    }


    private void lenderMenu(Library library, User user) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        String chose;
        System.out.printf("\nWelcome %s!", user.getName());
        while (isRunning) {
            System.out.println("\nMake one choice:");
            System.out.println("1. See available books.");
            System.out.println("2. Lend a book.");
            System.out.println("3. Return book.");
            System.out.print("4. See list of books that you haven't returned.");
            numberOfBooksUserHasBorrowed(user);
            System.out.println("5. Search for book (Title or ISBN).");
            System.out.println("6. Search for books (Title, Author or ISBN).");
            System.out.println("7. Read more about a book.");
            System.out.println("8. See all books in library.");
            System.out.println("9. Logg out");

            chose = scanner.nextLine();
            switch (chose) {
                case "1":
                    library.printoutTitle("Available books:");
                    library.showAvailableBooksInLibrary();
                    library.createReadingPauseForUser();
                    break;
                case "2":
                    lendABook(library, user);
                    break;
                case "3":
                    returnABook(library, user);
                    break;
                case "4":
                    user.printMyBooks();
                    library.createReadingPauseForUser();
                    break;
                case "5":
                    String bookSearchParameter = library.getInputFromUser("Search for book: ");
                    Book searchedBook = library.getSpecificBook(bookSearchParameter);
                    showBookSearchResults(bookSearchParameter, searchedBook);
                    library.createReadingPauseForUser();
                    break;
                case "6":
                    String booksSearchParameter = library.getInputFromUser("Search for book by title, author or ISBN: ");
                    List<Book> searchedBooks = library.searchForBook(booksSearchParameter);
                    showBookSearchResults(searchedBooks);
                    library.createReadingPauseForUser();
                    break;
                case "7":
                    library.showAllBooksInLibrary();
                    Pattern pattern = Pattern.compile("[^\\.\\!\\?]*[\\.\\!\\?]");
                    System.out.print("Title of book you want to read more about: ");
                    String tempTitle = scanner.nextLine();
                    Book book = library.getSpecificBook(tempTitle);
                    System.out.println("\n\t\t-----------------------------------------------------------------" +
                            "-----------------------------------------------------");
                    if (book != null) {
                        Message.messageWithColor("\n\t\tWritten by: " + book.getAuthor(), "yellow");
                        Matcher matcher = pattern.matcher(book.getDescription());
                        while (matcher.find()) {
                            System.out.println("\t\t" + matcher.group(0).trim());
                        }
                        if (book.getBookTracker().isAvailable()) {
                            Message.messageWithColor("\n\t\tThe book is available.", "blue");
                        } else {
                            DateFormat dayPattern = new SimpleDateFormat("yyyy-MM-dd");
                            Date returnDay = new Date(book.getBookTracker().getDateOfReturn());
                            Message.messageWithColor("\n\t\tThe book is not available. Should be returned " + dayPattern.format(returnDay), "red");
                        }
                    } else {
                        Message.messageWithColor("\n\t\tBook " + tempTitle + " was not found!", "red");
                    }
                    System.out.println("\t\t-----------------------------------------------------------------" +
                            "-----------------------------------------------------");
                    library.createReadingPauseForUser();
                    break;
                case "8":
                    library.printAllBooksInLibrary();
                    library.createReadingPauseForUser();
                    break;
                case "9":
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid selection!");
            }
        }
    }

    private void showBookSearchResults(String searchParameter, Book searchedBook) {
        Library library = Library.instance;

        if (searchedBook != null)
            Message.systemMessage(searchedBook);
        else {
            Message.messageWithColor("Couldn't find that book.", "red");
            List<Book> bookOptions = library.searchForBook(searchParameter);
            if (bookOptions.size() > 0) {
                Message.messageWithColor("Is this what you wanted?", "default");
                Message.systemMessage(bookOptions);
            }
        }
    }

    private void showBookSearchResults(List<Book> searchedBook) {
        if (searchedBook.size() > 0) {
            Message.messageWithColor("\t\tSearch result", "default");
            Message.systemMessage(searchedBook);
        } else
            Message.messageWithColor("Couldn't find a matching book or author", "red");
    }

    private void numberOfBooksUserHasBorrowed(User user) {
        int numberOfBooksLent = user.numberOfBorrowedBooks();
        if (numberOfBooksLent > 0 && user.numberOfLateBooks() > 0)
            Message.messageWithColor(String.format("(%d)", numberOfBooksLent), "red");
        else
            Message.messageWithColor(String.format("(%d)", numberOfBooksLent), "default");
    }

    private void lendABook(Library library, User user) {
        String tempTitle;
        Book book = null;
        library.printoutTitle("Lend a book:");
        List<Book> availableBooks = library.getAvailableBooks();
        library.showAvailableBooksInLibrary();
        tempTitle = library.getInputFromUser("Title of book: ");


        if(isNumber(tempTitle)){
            int index = Integer.parseInt(tempTitle) - 1;
            if (isInRangeOfList(availableBooks, index)) {
                book = library.getSpecificBook(availableBooks.get(index).getTitle());
            }
        }else {
            book = library.getSpecificBook(tempTitle);
        }

        if (book != null) {
            library.lendBookToUser(user, book);
            Message.systemMessageWithColor(book.getTitle() + " has been lent to you.", "blue");
        } else {
            Message.systemMessageWithColor("Book " + tempTitle + " not found, " +
                    "no book has been lent to you", "red");
        }

        library.createReadingPauseForUser();
    }

    private void returnABook(Library library, User user) {
        String tempTitle;
        Book book = null;
        library.printoutTitle("Return a book:");
        user.printMyBooks();
        tempTitle = library.getInputFromUser("Title of book: ");

        if(isNumber(tempTitle)){
            int index = Integer.parseInt(tempTitle) - 1;
            if (isInRangeOfList(user.getMyBooks(), index)) {
                book = library.getSpecificBook(user.getMyBooks().get(index).getTitle());
            }
        }else {
            book = library.getSpecificBook(tempTitle);
        }

        if (book != null) {
            library.returnBookFromUser(user, book);
            Message.systemMessageWithColor(book.getTitle() + " has been returned.", "blue");
        } else {
            Message.systemMessageWithColor("Book " + tempTitle + " not found, no book returned.", "red");
        }
        library.createReadingPauseForUser();
    }

    private boolean isNumber(String userInput) {
        try {
            Integer.parseInt(userInput);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isInRangeOfList(List<Book> availableBooks, int index) {
        return index < (availableBooks.size()) && index >= 0;
    }
}