package models;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Menu {
    Library library = new Library();

    public void login() {
        Scanner scanner = new Scanner(System.in);
        String userName;
        String password;
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
                    if (checkUserType(matchingUser)) {
                        librarianMenu(userName);
                    } else {
                        lenderMenu(userName);
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

    private void librarianMenu(String name) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        String chose;
        System.out.printf("\nWelcome %s!\n", name);
        while (isRunning) {
            System.out.println("Make one choice:");
            System.out.println("1. See available books");
            System.out.println("2. See all lent books");
            System.out.println("3. See all late returns");
            System.out.println("4. Lend a book");
            System.out.println("5. Return book");
            System.out.println("6. Add a new book to the library.");
            System.out.println("7. Remove book from library.");
            System.out.println("8. See all lender.");
            System.out.println("9. See lender by name.");
            System.out.println("15. Logg out");
            chose = scanner.nextLine();
            switch (chose) {
                case "1":

                    break;
                case "2":

                    break;
                case "3":

                    break;
                case "4":

                    break;
                case "5":

                    break;
                case "6":

                    break;
                case "7":

                    break;
                case "8":
                    library.getAllLenders();
                    break;
                case "9":
                    library.printUser(library.getUserNameInput());
                    break;
                case "15":
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid selection!");
            }

        }

    }

    private void lenderMenu(String name) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        String chose;
        System.out.printf("\nWelcome %s!\n", name);
        while (isRunning) {
            System.out.println("Make one choice:");
            System.out.println("1. See available books.");
            System.out.println("2. Lend a book.");
            System.out.println("3. Return book.");
            System.out.println("4. See list of books that you haven't returned.");
            System.out.println("5. Search book on title.");
            System.out.println("6. Search book on Author.");
            System.out.println("7. Read more about a book.");
            System.out.println("9. Logg out");
            chose = scanner.nextLine();
            switch (chose) {
                case "1":

                    break;
                case "2":

                    break;
                case "3":

                    break;
                case "4":

                    break;
                case "5":

                    break;
                case "6":

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
}