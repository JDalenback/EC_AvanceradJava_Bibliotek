package models;

import java.io.Serializable;
import java.io.*;

import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Library implements Serializable {
    private List<Book> booksInLibrary = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private Map<String, List<LibraryWatcher>> watchers = new HashMap<>();

    public Library() {
        watch("insert", event ->
                serializeObject(this, "src/models/books.ser"));

        watch("delete", event ->
                serializeObject(this, "src/models/books.ser"));
    }

    public void watch(String event, LibraryWatcher watcher) {
        watchers.putIfAbsent(event, new ArrayList<>());
        watchers.get(event).add(watcher);
    }

    public void callWatchers(String event, Object data) {
        watchers.get(event).forEach(watcher ->
                watcher.handle(new LibraryEvent(event, data)));
    }

    public void showToUser(List<?> list) {
        list.forEach(System.out::println);
    }

    public void showToUser(Object object) {
        System.out.println(object);
    }

    public void showAllBooksInLibrary() {
        showToUser(booksInLibrary);
    }

    public void showAvailableBooksInLibrary() {
        List<Book> availableBooks = booksInLibrary
                .stream()
                .filter(book -> (book.getBookTracker().isAvailable()))
                .collect(Collectors.toList());

        showToUser(availableBooks);
    }
    public void showAllLentBooksInLibrary() {
        List<Book> lentBooks = booksInLibrary
                .stream()
                .filter(book -> !(book.getBookTracker().isAvailable()))
                .collect(Collectors.toList());

        showToUser(lentBooks);
    }

    public void showAllUsers() {
        showToUser(users);
    }

    public List<Book> searchForBook(String searchParameter) {
        List<Book> searchResult = booksInLibrary
                .stream()
                .filter(book -> book.getTitle().contains(searchParameter) ||
                        book.getAuthor().contains(searchParameter) ||
                        book.getIsbn().contains(searchParameter))
                .collect(Collectors.toList());

        if (searchResult.size() > 0) {
            showToUser(searchResult);
        } else
            System.out.println("Can't find that book in the library.");

        return searchResult;
    }

    public Book getSpecificBook(String searchParameter) {
        List<Book> books = booksInLibrary
                .stream()
                .filter(book -> book.getTitle().matches(searchParameter) ||
                        book.getAuthor().matches(searchParameter) ||
                        book.getIsbn().matches(searchParameter))
                .collect(Collectors.toList());

        if (books.size() > 0)
            return books.get(0);
        else
            return null;
    }

    public void showStatusOfBooks() {
        booksInLibrary.forEach(book -> {
            System.out.println(book);
            System.out.println(book.getBookTracker());
            System.out.println();
        });
    }

    public void lendBookToUser(User user, Book book) {
        BookTracker bookTracker = book.getBookTracker();
        bookTracker.setAvailable(false);
        bookTracker.setUserThatBorrowed(user);
        bookTracker.setDateOfReturn(setBookReturnTime());
        user.addBookToMyBooks(book);
    }

    public void returnBookFromUser(User user, Book book) {
        BookTracker bookTracker = book.getBookTracker();
        bookTracker.setAvailable(true);
        bookTracker.setUserThatBorrowed(null);
        bookTracker.setDateOfReturn(0);
        user.removeBookFromMyBooks(book);
    }


    private int indexOfBookName(String title) {
        return IntStream.range(0, booksInLibrary.size())
                .filter(i -> booksInLibrary.get(i).getTitle().equalsIgnoreCase(title))
                .findFirst().orElse(-1);
    }

    // to be changed to title when search function is added
    public void removeBookFromLibrary() {
        Scanner scanner = new Scanner(System.in);
//        String title;
//        System.out.println("\nRemove book.");
//        System.out.print("Title: ");
//        title = scanner.nextLine();
//        int indexNo = indexOfBookName(title);
//        if (indexNo > 0) {
//            booksInLibrary.remove(indexNo);
//            System.out.printf("Book %s has been removed from list.\n\n", title);
//        } else {
//            System.out.printf("Book %s can't be found in the library!\n\n", title);
//        }

        System.out.println("\nRemove book.");
        System.out.print("Title: ");
        String title = scanner.nextLine();
        Book book = getSpecificBook(title);
        if (book != null) {
            booksInLibrary.remove(book);
            System.out.printf("Book %s has been removed from list.\n\n", title);
            callWatchers("delete", booksInLibrary);
        } else
            System.out.println("Book not found");
    }

    public void addNewBookToLibrary() {
        Scanner scanner = new Scanner(System.in);
        BookTracker bookTracker = new BookTracker();
        String bookTitle;
        String author;
        String isbn;
        String description;

        System.out.println("\nAdd new book.");
        System.out.print("Title: ");
        bookTitle = scanner.nextLine();
        System.out.print("Autor: ");
        author = scanner.nextLine();
        System.out.print("ISBN: ");
        isbn = scanner.nextLine();
        System.out.print("Description: ");
        description = scanner.nextLine();

        Book newBook = new Book(bookTitle, author, isbn, description);
        booksInLibrary.add(newBook);
        System.out.printf("Book %S added to list.\n\n", bookTitle);

        callWatchers("insert", booksInLibrary);
    }

//create new users and put them in list of allUsers


    public Long setBookReturnTime() {
        long timeNow = System.currentTimeMillis();
        return timeNow + 14 * 24 * 60 * 60 * 1000; // one day = 86400000 ms
    }

    public void lendingStatusDate(long lendingPeriodInMs) {
        DateFormat dayPattern = new SimpleDateFormat("yyyy-MM-dd");
        Date returnDay = new Date(lendingPeriodInMs);

        long timeNow = System.currentTimeMillis();

        if (timeNow > lendingPeriodInMs) {
            System.out.println("\nYour book is late!\nReturn to the library immediately.");
        } else if (lendingPeriodInMs - timeNow < 259200000) { // 259200000 ms = three days
            System.out.printf("\nYour loan period is almost over.\n" +
                    "Please return the book at the latest %s.\n", dayPattern.format(returnDay));
        } else {
            System.out.printf("\nReturn the book latest %s.\n", dayPattern.format(returnDay));
        }
    }

    public void getAllLenders() {
        Stream<User> tempTest;
        tempTest = users
                .stream()
                .filter(user -> !user.isAdmin());
        tempTest.forEach(user ->
                System.out.println("-- Name: " + user.getName() + ", ID: " + user.getUserID() + ", Books: " + user.getMyBooks()));
    }

    private int indexOfUser(String inputID) {
        return IntStream.range(0, users.size())
                .filter(i -> users.get(i).getUserID().equalsIgnoreCase(inputID))
                .findFirst().orElse(-1);
    }

    //create new visitor and put it in list of users
    public void addUser() {
        String name;
        String userID;
        String admin;
        boolean adminBoolean = false;

        Scanner scan = new Scanner(System.in);

        System.out.print("---Create a new USER---\n\nName: ");
        name = scan.nextLine();
        System.out.print("UserID: ");
        userID = scan.nextLine();
        System.out.println("Admin? Enter \"yes\" or \"no\"");
        admin = scan.nextLine();


        if (admin.equalsIgnoreCase("yes"))
            adminBoolean = true;

        User newUser = new User(name, userID, adminBoolean);
        users.add(newUser);
        System.out.println("\n" + name + " is now added to the system \n");
    }

    public void removeUser() {
        Scanner scanner = new Scanner(System.in);
        String userID;
        System.out.println("\n--- Remove User ---\n");
        System.out.print("UserID: ");
        userID = scanner.nextLine();
        int indexNo = indexOfUser(userID);
        if (indexNo > 0) {
            users.remove(indexNo);
            System.out.print("User is removed.\n\n");
        } else {
            System.out.print("User was not found.\n\n");
        }
    }

    public String getUserNameInput() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Name: ");
        String tempName = scan.nextLine();
        return tempName;
    }

    public void printUser(String userName) {
        Optional<User> user = users.stream().filter(u -> u.getName().equals(userName)).findFirst();
        if (user.isPresent()) {
            System.out.println("\n--- Name: " + userName + ", UserID: " + user.get().getUserID() + ", Books: " + user.get().getMyBooks() + "\n");
        } else
            System.out.println("Sorry, user not found.");
    }


    public User getSpecificUser(String userID) {
        Optional<User> user = users.stream().filter(u -> u.getUserID().equals(userID)).findFirst();
        if (user.isPresent())
            return user.get();
        else
            return null;
    }

    // To be removed when save/read file is implemented.

    public static void serializeObject(Object library, String fileName) {
        try (FileOutputStream fileOutStream = new FileOutputStream(fileName); ObjectOutputStream objectOutStream = new ObjectOutputStream(fileOutStream)) {
            objectOutStream.writeObject(library);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Library deSerializeObject() {
        Library library = null;
        try (ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream("src/models/books.ser"))) {
            library = (Library) objectInput.readObject();
        } catch (FileNotFoundException e) {
            // New Library Will be created if file is not found.
        } catch (Exception e) {
            e.printStackTrace();
        }

        return library;

    }


    public void populateMockupLibrary() {
        readInBooks();
        users.add(new User("John Doe", "12345", false));
        users.add(new User("Molly", "23456", true));
        users.add(new User("Andy", "34567", true));
        users.add(new User("Misty", "45678", false));

        User user = getSpecificUser("12345");
        Book book = getSpecificBook("Vita tänder");
        lendBookToUser(user, book);
    }

    public void printoutTitle(String title) {
        System.out.printf("\n%s\n\n", title);
    }

    public void createReadingPausForUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nPress enter to continue.");
        String nothing = scanner.nextLine();
    }

    public List<User> getUsers() {
        return users;
    }

    private void readInBooks() {
        Book book1 = new Book("Vita tänder", "Zadie Smith", "9789175036434", "I en myllrande del av London möts medlemmar från familjerna Jones, Iqbal " +
                "och Chalfens. De har olika bakgrund, religion och hudfärg men deras liv vävs samman i en oförutsägbar berättelse. " +
                "Vänskapen mellan två omaka män, Archie Jones och Samad Iqba, går som en röd tråd genom romanen som spänner sig över ett halvt sekel.");
        Book book2 = new Book("Norwegian Wood", "Haruki Murakami", "9789113089461", "Boken som gjorde Haruki Murakami till en superstjärna i litteraturen. " +
                "När Toru av en slump träffar sin väns före detta flickvän Naoko blir han huvudlöst förälskad. Deras kärlek är lika delar öm, intensiv och omöjlig. Naoko är vacker men har" +
                " ett bräckligt psyke och medan hon försvinner in i vårdhem skriver Toru brev, gör korta besök och väntar. Läs boken för skildringarna av relationer, " +
                "kärlek, sex och känslomässigt beroende.");
        Book book3 = new Book("Återstoden av dagen", "Kazuo Ishiguro", "9789174297126", "2017 års mottagare av Nobelpriset i litteratur Kazuo Ishiguro ligger bakom denna " +
                "storsäljare. Butlern Stevens ger sig ut på sitt livs första semester i sin husbondes bil. Läsaren får följa med på en resa genom 1950-talets England såväl som genom Stevens " +
                "egna minnen.");
        Book book4 = new Book("Glaskupan", "Sylvia Plath", "9789174293418", "I en tävling vinner 19-åriga Esther en månads praktik på en tidskrift i New York. Hon har ett klarögt sätt" +
                " att se på världen och gör träffande beskrivningar av det hon upplever och människorna hon möter. Esther för dock en ständig kamp mot sin psykiska ohälsa. Boken väcker många frågor om " +
                "rollerna vi människor, och särskilt kvinnor, förväntas kliva in i — och vad som händer när vi misslyckas.");
        Book book5 = new Book("Ett år av magiskt tänkande", "Joan Didion", "9789173893091", "År 2003 ligger Joan och maken Johns enda dotter på sjukhus, svävande mellan liv och död. En kväll drabbas" +
                " John av en massiv hjärtinfarkt och dör. Ett år av magiskt tänkande är Joan Didions försök att förstå tiden som följde. En bok om sorg, mörker och liv skriven på ett rått och rakt sätt.");


        booksInLibrary.add(book1);
        booksInLibrary.add(book2);
        booksInLibrary.add(book3);
        booksInLibrary.add(book4);
        booksInLibrary.add(book5);
    }
}