package models;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Menu implements Serializable {
    private static final long serialVersionUID = 1L;
    public void login(Library library) {
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
            System.out.println("15. Logg out");
            chose = scanner.nextLine();
            switch (chose) {
                case "1":
                    library.printoutTitle("\t\tAvailable books:");
                    library.showAvailableBooksInLibrary();
                    library.createReadingPausForUser();
                    break;
                case "2":
                    library.printoutTitle("\t\tLent books:");
                    library.showAllLentBooksInLibrary();
                    library.createReadingPausForUser();
                    break;
                case "3":
                    library.printoutTitle("\t\tLate books:");
                    library.showAllLateBooks();
                    library.createReadingPausForUser();
                    break;
                case "4":
                    lendABook(library, user);
                    break;
                case "5":
                    returnABook(library, user);
                    break;
                case "6":
                    library.addNewBookToLibrary();
                    library.createReadingPausForUser();
                    break;
                case "7":
                    library.removeBookFromLibrary();
                    library.createReadingPausForUser();
                    break;
                case "8":
                    library.getAllLenders();
                    library.createReadingPausForUser();
                    break;
                case "9":
                    library.printUser(library.getInputFromUser("Name: "));
                    library.createReadingPausForUser();
                    break;
                case "10":
                    library.addUser();
                    library.createReadingPausForUser();
                    break;
                case "11":
                    library.removeUser();
                    library.createReadingPausForUser();
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

            System.out.println("5. Search book on title.");
            System.out.println("6. Search book on Author.");
            System.out.println("7. Read more about a book.");
            System.out.println("9. Logg out");

            chose = scanner.nextLine();
            switch (chose) {
                case "1":
                    library.printoutTitle("Available books:");
                    library.showAvailableBooksInLibrary();
                    library.createReadingPausForUser();
                    break;
                case "2":
                    lendABook(library, user);
                    break;
                case "3":
                    returnABook(library, user);
                    break;
                case "4":
                    library.printMyBooks(user);
                    library.createReadingPausForUser();
                    break;
                case "5":
                    library.showOneObjectToUser(library.getSpecificBook(library.getInputFromUser("\t\tTitle: ")));
                    library.createReadingPausForUser();
                    break;
                case "6":
                    library.showOneObjectToUser(library.getSpecificBook(library.getInputFromUser("\t\tAuthor: ")));
                    library.createReadingPausForUser();
                    break;
                case "7":

                    break;
                case "8":

                    break;
                case "9":
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid selection!");
            }
        }
    }

    private void numberOfBooksUserHasBorrowed(User user) {
        int numberOfBooksLent = user.numberOfBorrowedBooks();
        if (numberOfBooksLent > 0 && user.numberOfLateBooks() > 0)
                System.out.printf(TextColors.ANSI_RED + "(%d)\n" + TextColors.ANSI_RESET, numberOfBooksLent);
            else
                System.out.printf("(%d)\n", numberOfBooksLent);
    }

    private void lendABook(Library library, User user) {
        String tempTitle;
        Book book;
        library.printoutTitle("Lend a book:");
        library.showAvailableBooksInLibrary();
        tempTitle = library.getInputFromUser("Title of book: ");
        book = library.getSpecificBook(tempTitle);
        if (book != null) {
            library.lendBookToUser(user, book);
            library.printoutTitle(book.getTitle() + " has been lent to you.");
        } else {
            library.printoutTitle("Book " + tempTitle + " not found, " +
                    "no book has benn lent to you");
        }
        library.createReadingPausForUser();
    }

    private void returnABook(Library library, User user) {
        String tempTitle;
        Book book;
        library.printoutTitle("Return a book:");
        user.getMyBooks().stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
        tempTitle = library.getInputFromUser("Title of book: ");
        book = library.getSpecificBook(tempTitle);
        if (book != null) {
            library.returnBookFromUser(user, book);
            library.printoutTitle(book.getTitle() + " has been returned.");
        } else {
            library.printoutTitle("Book " + tempTitle + " not found, no book returned.");
        }
        library.createReadingPausForUser();
    }
}
