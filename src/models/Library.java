package models;

import Utils.LibraryFileUtils;

import java.io.*;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Library implements Serializable {
    private static Library libraryInstance = null;
    private List<Book> booksInLibrary = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private Map<String, List<LibraryWatcher>> watchers = new HashMap<>();

    private Library() {
        initializeWatchers();
    }

    private void initializeWatchers() {
        watch("insert", event ->
                LibraryFileUtils.serializeObject(this));

        watch("delete", event ->
                LibraryFileUtils.serializeObject(this));

        watch("lendBook", event ->
                LibraryFileUtils.serializeObject(this));

        watch("returnBook", event ->
                LibraryFileUtils.serializeObject(this));
    }

    public void checkIfUserNameExists(Object name) {
        String[] nameSplit = name.toString().split("\'");
        List<Object> tempName = new ArrayList<>(users);
        for (Object item : tempName) {
            if (item.toString().contains(nameSplit[1])) {
                try {
                    throw new Exception("NAME ALREADY BOUND TO USER. CHOOSE ANOTHER NAME.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
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
        System.out.println("\t\t----------------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("\t\t" + (i + 1) + ".\t" + list.get(i));
        }
        System.out.println("\t\t----------------------------------------------------------------------------------------------------------------------");

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

    public void showAllLateBooks() {
        long currentTime = System.currentTimeMillis();
        List<Book> lateBooks = booksInLibrary
                .stream()
                .filter(book -> book.getBookTracker().getDateOfReturn() > 0 && book.getBookTracker().getDateOfReturn() < currentTime)
                .collect(Collectors.toList());

        showToUser(lateBooks);
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

    public boolean isBookAvailable(Book book) {
        return book.getBookTracker().isAvailable();
    }

    public void lendBookToUser(User user, Book book) {

        BookTracker bookTracker = book.getBookTracker();
        bookTracker.setAvailable(false);
        bookTracker.setUserThatBorrowed(user);
        bookTracker.setDateOfReturn(setBookReturnTime());
        user.addBookToMyBooks(book);

        callWatchers("lendBook", book);
    }

    public void returnBookFromUser(User user, Book book) {
        BookTracker bookTracker = book.getBookTracker();
        bookTracker.setAvailable(true);
        bookTracker.setUserThatBorrowed(null);
        bookTracker.setDateOfReturn(0);
        user.removeBookFromMyBooks(book);

        callWatchers("returnBook", book);
    }


    private int indexOfBookName(String title) {
        return IntStream.range(0, booksInLibrary.size())
                .filter(i -> booksInLibrary.get(i).getTitle().equalsIgnoreCase(title))
                .findFirst().orElse(-1);
    }

    // to be changed to title when search function is added
    public void removeBookFromLibrary() {
        Scanner scanner = new Scanner(System.in);
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
        // BookTracker bookTracker = new BookTracker();
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

        callWatchers("insert", newBook);
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
            System.out.println("\t-\tYour book is late! Return to the library immediately.");
        } else if (lendingPeriodInMs - timeNow < 259200000) { // 259200000 ms = three days
            System.out.printf("\t-\tYour loan period is almost over.\n" +
                    "Please return the book at the latest %s.\n", dayPattern.format(returnDay));
        } else {
            System.out.printf("\t-\tReturn the book latest %s.\n", dayPattern.format(returnDay));
        }
    }

    public void printMyBooks(User user) {
        System.out.println("\t\t----------------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < user.getMyBooks().size(); i++) {
            long temp = user.getMyBooks().get(i).getBookTracker().getDateOfReturn();
            System.out.print("\t\t" + (i + 1) + ".\t" + user.getMyBooks().get(i).getTitle() + ", written by " + user.getMyBooks().get(i).getAuthor());
            lendingStatusDate(temp);
        }
        System.out.println("\t\t----------------------------------------------------------------------------------------------------------------------");
    }

    public void getAllLenders() {
        Stream<User> tempTest;
        tempTest = users
                .stream()
                .filter(user -> !user.isAdmin());
        tempTest.forEach(user ->
                System.out.println("-- Name: " + user.getName() + ", ID: " + user.getUserID() + ", Books: " + user.getMyBooks() + "\n"));
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

        System.out.print("\n---Create a new USER---\n\nName: ");
        name = scan.nextLine();
        userID = makeValidUserID();
        System.out.println("Admin? Enter \"yes\" or \"no\"");
        admin = scan.nextLine();

        if (admin.equalsIgnoreCase("yes"))
            adminBoolean = true;

        User newUser = new User(name, userID, adminBoolean);
        users.add(newUser);
        System.out.println("\n" + name + " is now added to the system \n");

        callWatchers("insert", newUser);
    }

    public String makeValidUserID() {
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        String userID = null;
        System.out.println("Chose a UserID.");
        while (isRunning) {
            System.out.print("Your UserID must be at least 8 character long.\n" +
                    "Containing at lest one special character @$!%*?&, one uppercase, one lowercase and one number 0-9.\nUserID: ");
            userID = scanner.nextLine();
            Matcher matcher = pattern.matcher(userID);
            if (matcher.find()) {
                System.out.println("Password accepted\n");
                isRunning = false;
            } else {
                System.out.println("\nInvalid content in your UserID!");
            }
        }
        return userID;
    }

    // This method should have the user to be removed as a parameter.
    // We should not rely on the index of the object in the List.
    public void removeUser() {
        Scanner scanner = new Scanner(System.in);
        String userID;
        System.out.println("\n--- Remove User ---\n");
        System.out.print("UserID: ");
        userID = scanner.nextLine();
        int indexNo = indexOfUser(userID);
        if (indexNo >= 0) {
            users.remove(indexNo);
            System.out.print("User is removed.\n\n");
        } else {
            System.out.print("User was not found.\n\n");
        }

        callWatchers("delete", users);
    }

    public String getInputFromUser(String input) {
        Scanner scan = new Scanner(System.in);
        System.out.print(input);
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

    // To be removed when save/read file is implemented.


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
        Book book2 = getSpecificBook("Återstoden av dagen");
        lendBookToUser(user, book);
        lendBookToUser(user, book2);
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


    public static Library getLibraryInstance() {
        if (libraryInstance == null)
            libraryInstance = new Library();
        return libraryInstance;
    }


    // Test method. Use to populate library with a few books and users. To be removed!

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
        Book book6 = new Book("Svindlande höjder", "Emily Brontë", "9789174290868", "Kärlek, passion och död på heden. Heathcliff kommer som barn till familjen Earnshaw. Han är avskydd av sin fosterbror" +
                " men älskad av sin fosterfar och fostersystern och tvillingsjälen Catherine. Vad som följer är en uppslitande, passionerad och klassisk kärlekshistoria mellan Heathcliff och Catherine.");
        Book book7 = new Book("Röda Rummet", "August Strindberg", "9789188680501", "August Strindbergs kanske mest kända verk som ofta kallas för den första svenska moderna romanen. Vi får följa Arvid Falk," +
                " Strindbergs alter ego, i sin strävan efter frihet och sanning. Han rör sig mellan konstnärskolonin vid Lill-Jans och fattigkvarteren på Södermalm till ett värdshus i landsorten och en ö i skärgården. Ständigt med " +
                "skarpa iakttagelser om samhället.");
        Book book8 = new Book("Maken", "Gun-Britt Sundström", "9789174297409", "I 1960-talets Stockholm, en tid då många tror att kärnfamiljen är på väg att avskaffas, träffas Martina och Gustav. De ses," +
                " blir ihop, skiljs åt och försonas — om och om igen. De funderar över vad det innebär att vara ett par, vad man kan kräva av varandra och om det förändras när man gifter sig. Maken är en relationsroman som ibland kommer" +
                " få dig att nicka igenkännande och andra gånger slita ditt hår av frustration.");
        Book book9 = new Book("Ett litet liv", "Hanya Yanagihara", "9789174296297", "Gör dig redo att känna mycket och gråta ordentligt. Den här berättelsen följer de fyra vännerna JB, Malcolm, Willem och Jude och" +
                " deras liv i New York. Genom åren prövas delas vänskap men Jude, en framgångsrik advokat med ett traumatiskt förflutet, är alltid personen som håller de samman. Tips: ha näsdukar nära till hands!");
        Book book10 = new Book("Anne Franks dagbok", "Anne Frank", "9789113081212", "Ett av de viktigaste dokumenten vi har från Förintelsen. Sommaren 1942 går Anne och hennes familj under jorden för att gömma" +
                " sig för nazisterna. 13-åriga Anne skriver i sin dagbok vad som händer och vi får följa hennes utveckling till ung kvinna och skarp iakttagare av människans natur.");
        Book book11 = new Book("En halv gul sol", "Chimamanda Ngozi Adichie", "9789100118679", "Olanna och Kainene är så olika varandra som tvillingsystrar kan vara, med helt olika drömmar och ambitioner. " +
                "Kainene möter engelsmannens Richard, som blir förälskad i henne, och Olanna får en dotter med sin stora kärlek. Men när inbördeskriget bryter ut i Nigeria slits deras liv i stycken.");
        Book book12 = new Book("Jane Eyre", "Charlotte Brontë", "9780141198859", "En milstolpe när det kommer till skildringar av kvinnor i litteraturen. Föräldralösa Jane har en svår och kärlekslös uppväxt." +
                " Hon får en tjänst som privatlärare åt en liten flicka på Thornfield, där möter hon för första gången Mr Rochester. De kommer allt närmare varandra men hemligheter i Mr Rochesters liv sätter käppar i hjulet för deras relation. " +
                "Läs boken för den moderna skildringen av Jane som en självständig och passionerad kvinna.");
        Book book13 = new Book("Alkemisten", "Paulo Coelho", "9789170285752", "En berättelse om Santiago, en andaluaisk herdepojke som reser från sitt hem i Spanien till marknaderna i Tanger och genom Egyptens öken i jakten" +
                " på en av världens dyrbaraste skatter. Vägen är kantad av flera värdefulla skatter och lärdomar. Den magiska boken är en av världens mest sålda böcker.");
        Book book14 = new Book("Flyga drake", "Khaled Hosseini", "9789170017032", "Amir och Hassan kommer från vitt skilda världar men växer trots det upp som bästa vänner. När Sovjetstyrkor invaderar flyr Amir och hans far landet" +
                " för att istället etablera sig i Kalifornien. Men i sitt nya liv har Amit svårt att släppa minnet av Hassan. En gripande skildring av vänskap, lojalitet och relationen mellan fäder och söner.");
        Book book15 = new Book("Tjänarinnans berättelse", "Margaret Atwood", "9789113082905", "Boken som ligger bakom tv-succén The Handmaid’s Tale. I ett framtida USA har den kristet fundamentalistiska regimen Gilead tagit makten." +
                " I ett försök att stävja de sjunkande födelsetalen hålls fertila kvinnor som slavar och tvingas föda barn åt de högt uppsatta männen i Gilead och deras fruar.");
        Book book16 = new Book("Ringarnas herre", "J.R.R. Tolkien", "9789113084909", "Om du bara ska läsa en fantasyserie så är det denna. Historien om Frodo och Härskarringen är sedan länge klassisk, precis som dess teman om godhet, " +
                "ondska, vänskap och kärlek.");
        Book book17 = new Book("Boktjuven", "Markus Zusak", "9789132211676", "En bok om vänskap och mod i krigets Nazityskland, berättad av Döden själv. 9-åriga Liesel Memingers liv förändras när hon plockar upp boken Dödgrävarens " +
                "handbok som ligger på marken bredvid broderns grav. Det är hennes första bokstöld, men inte hennes sista.");
        Book book18 = new Book("Ett eget rum", "Virginia Woolf", "9789187193484", "En roman skriven av en kvinna, för andra kvinnor. I denna essä skriver Virginia Woolf om kvinnors roll i samhället och kvinnlig frigörelse från patriarkatet.");
        Book book19 = new Book("Räddaren i nöden", "JD Salinger", "9789174293814", "En av våra största generationsromaner vars teman som utanförskap, identitet, ångest och förlust väcker igenkänning hos många. I Räddaren i nöden har 16-åriga " +
                "Holden precis blivit relegerad från sin skola. Istället för att möta upp sina föräldrar i New York ger han sig ut på egna äventyr för att under några hektiska dygn leva som vuxen.");
        Book book20 = new Book("En komikers uppväxt", "Jonas Gardell", "9789113100654", "Jonas Gardells skildring av en generations uppväxt i 1970-talets svenska förorter. Juha Lindström är 12 år gammal och klassens clown. Att vara rolig är " +
                "det han är bra på. Genom honom får vi lära känna mamma Ritva, pappa Bengt, lillasyster Marianne och dvärgkaninen Prutten. Även klasskamraterna en stor del av Juhas liv: bästa vännen Jenny med det fula hårspännet, den mobbade Thomas och de elaka barnen" +
                " Lennart och Stefan.");
        Book book21 = new Book("Just Kids", "Patti Smith", "9789173373180", "Patti Smiths självbiografi som skildrar hennes enastående liv och karriär. 1967 lämnar Patti Smith sin hemstad, hoppar av lärarutbildningen och flyttar till New York. " +
                "20 år gammal längtar hon efter nya intryck, kreativitet och utveckling. I staden träffar hon fotografen Robert Mapplethorp. De bor tillsammans i många år, inspirerar varandra och når båda två många framgångar.");
        Book book22 = new Book("Drömfakulteten", "Sara Stridsberg", "9789174297331", "Sara Stridsbergs genombrottsroman om Valerie Solanas liv. Hon växer upp i USA:s öken på 1950-talet. Efter en resa bestämmer hon sig för att" +
                " lämna sitt hem och försörja sig som prostituerad. Vi får följa Valerie genom tonåren, livet som psykologistudent och tiden som inlagd på mentalsjukhus.");
        Book book23 = new Book("Den store Gatsby", "F. Scott Fitzgerald", "9789171370129", "En klassisk roman om 1920-talets USA. Nick Carraway kommer till New York för att bli aktiemäklare. Han flyttar in i ett hus på Long Island och lär " +
                "känna det flärdfulla paret Tom och Daisy Buchanan. Nicks granne är den lika delar gåtfulla som rika mannen Jay Gatsby.");
        Book book24 = new Book("Doktor Glas", "Hjalmar Söderberg", "9789188680549", "En roman vars centrala problem — om man har rätt att döda en människa för att rädda en annan — väckt debatt genom flera decennier." +
                " Doktor Glas förälskar sig i den vackra och unga Helga, som i sin tur är fast i ett kärlekslöst äktenskap med pastor Gregorius.");
        Book book25 = new Book("1984", "George Orwell", "9789173539678", "Konstellationerna förändras, men kriget mellan staterna Eurasien, Ostasien och Oceanien upphör aldrig. Oceanien är ett samhälle styrt av en oligarkisk kommunistisk " +
                "diktatur med Storebror som högsta ledare. Invånarna är under konstant övervakning av regeringen och tvingas att ständigt lyssna på propaganda. En skrämmande framtidsskildring som haft stor påverkan på kulturen.");
        Book book26 = new Book("Att döda en härmtrast", "Harper Lee", "9789100168186", "I en småstad i amerikanska södern blir en ung svart man anklagad för våldtäkt på en vit kvinna. Hans chans att få en rättvisande rättegång är liten," +
                " och när en vit advokat tar sig an uppdraget växer hatet i det lilla samhället.");
        Book book27 = new Book("Det mest förbjudna", "Kerstin Thorvall", "9789174293364", "Boken som med sin skildring av kvinnlig sexualitet på 1970-talet väckte moralpanik. I Det mest förbjudna kastar sig den snälla och lydiga dottern" +
                " Anna ut i en försenad tonårsrevolt. Hennes tidigare undertryckta sexualitet kommer upp till ytan och hon söker sig till flera olika destruktiva män.");
        Book book28 = new Book("Allt går sönder", "Chinua Achebe", "9789174293364", "Okonkwo är den stoltaste och mest fruktade krigaren i hela västra Afrika och en viktig man i sin klan. Hans starka drivkraft är att aldrig bli som sin vekling till far. När vita " +
                "missionärer kommer till byn med nya seder och institutioner ser Okonkwo det som sin uppgift att göra motstånd.");
        Book book29 = new Book("Stolthet och fördom", "Jane Austen", "9789174293364", "En av litteraturens mest kända och älskade kärleksromaner. Mrs Bennets främsta mål är att hennes döttrar ska gifta sig med rika män. Hon ser sin chans när" +
                " Mr Bingley tillsammans med vännen Mr Darcy flyttar in i ett närliggande gods. Den intelligenta och frispråkiga Elizabeth Bennet tycker till en början att Mr Darcy är snobbig och högfärdig, men allt eftersom de lär känna varandra förändras hennes känslor.");
        Book book30 = new Book("Vägen", "Cormac McCarthy", "9789100121525", "I ett postapokalyptiskt USA vandrar en far och hans son genom kyla, snö och aska. De går mot kusten, men vad som väntar där vet de inte riktigt. Det enda de har med sig " +
                "är kläderna de bär på kroppen, en kärra med mat och en pistol att försvara sig med mot de laglösa gäng som rör sig längs vägen. En träffande berättelse om överlevnad, en skrämmande framtid och kärleken mellan far och son.");
        Book book31 = new Book("Min kamp", "Karl Ove Knausgård", "9789113088563", "Karl Ove Knausgårds mäktiga självbiografiska roman i sex delar är inte det lättaste att ta sig igenom, men serien är en av de senaste årens mest omdiskuterade. " +
                "I den första boken står döden i centrum. När Karl Ove får beskedet att hans far, som alltid har agerat onåbar, har gått bort inleds ett sorgearbete som sätter minnet på spel.");
        Book book32 = new Book("Vindens skugga", "Carlos Ruiz Zafón", "9789113046488", "När Daniel Sempere är 10 år gammal tar hans far med honom till De bortglömda böckernas gravkammare i en gammal del av Barcelona. Det är där han hittar boken Vindens " +
                "skugga av den mystiske och bortglömda författaren Juliàn Carax. Daniel blir besatt av att söka efter svar på de gåtor som omger boken och dess författare.");
        Book book33 = new Book("Harry Potter och De vises sten", "J.K. Rowling", "9789129697704", "Harry Potter och De vises sten är första boken av sju och serien har fångat läsare i mer än femton år - perfekt att högläsa tillsammans. " +
                "Sexton år efter utgivningen av den första Harry Potter-boken i Sverige kommer nu en alldeles ny, genomillustrerad utgåva. Harry Potters fantastiska värld fångas mästerligt i Jim Kays makalösa bilder. Låt dig sugas in i en magisk upplevelse! " +
                "Böckerna om Harry Potter har sålt i hundratals miljoner exemplar världen över. Den föräldralöse pojken som visar sig vara en trollkarl tog världen med storm, och lämnade ingen oberörd. Böckerna har blivit klassiker, och fortsätter att förtrolla nya generationer läsare.");
        Book book34 = new Book("Min fantastiska väninna", "Elena Ferrante", "9789113073347", "Den första delen i Elena Ferrantes populära Neapelkvartett. Elena och Lila växer upp i 1950-talets Neapel. De är bästa vänner men också varandras ständiga" +
                " konkurrent och de tävlar ofta mot varandra. Under barndomen och tonåren får vi följa de båda vännerna när de tampas med skolan, familjen, kärlek och kvarterets drama.");
        Book book35 = new Book("Kallocain", "Karin Boye", "9789174290875", "Innan George Orwell skrev 1984 skrev Karin Boye sin dystopiska roman Kallocain om ett polisstyrt övervakningssamhälle. Kemisten Leo Kall har uppfunnit kallocain, en" +
                " sanningsdrog som öppnar vägen in till medborgarnas själar. Det visar sig att de flesta har drömmar om uppror mot Världsstaten och en önskan om en avlägsen plats för kärlek och frihet. Leo Kall börjar själv tvivla på Staten och ifrågasätta sin lojalitet.");
        Book book36 = new Book("Egenmäktigt förfarande", "Lena Andersson", "9789127138537", "Vinnare av Augustpriset 2013 och en av de senaste årens mest omtalade romaner. När poeten och essäisten Ester Nilsson håller ett föredrag om konstnären Hugo" +
                " Rask möts de två för första gången. Det är början på ett slags kärlekshistoria där Lena Andersson skriver om makt, besatthet, ensamhet och vår önskan att bli älskade.");
        Book book37 = new Book("Bridget Jones dagbok", "Helen Fielding", "9789137143217", "Berättelsen om Bridget Jones ses av många som starten för chick lit-genren. 32 år gammal, singel, kedjerökande och ständigt bantande beställer sig Bridget för" +
                " att ta kontroll över sitt liv genom att skriva dagbok. Hon slits mellan sin snygga men sliskiga chef Daniel och den vid en första anblick tråkiga, men trygga, Mark.");
        Book book38 = new Book("Mina drömmars stad", "Per Anders Fogelström", "9789100123758", "Den första delen i Per Anders Fågelströms romanserie om Henning Nilsson och hans släkt. Vi möter Henning första gången en sommarkväll 1860. Han kommer " +
                "vandrande till Stockholm, staden han har drömt om, men får slita hårt i vad för många är en mörk och fattig stad. Tillsammans med sin fru Lotten försöker han skapa en ljus tillvaro. en klassisk skildring av Stockholm och arbetarklassen.");
        Book book39 = new Book("Jag är Zlatan", "Zlatan Ibrahimovic och David Lagercrantz", "9789175032849", "Den mest närgående berättelsen om en av Sveriges största idrottsstjärnor. Zlatan berättar om uppväxten i Rosengård, spelet bakom klubbyten," +
                " tiden i Italien och livet bakom strålkastarljuset med Helena och deras barn.");
        Book book40 = new Book("Kejsaren av Portugallien", "Selma Lagerlöf", "9789176390764", "Den värmländska torparen Jan älskar sin dotter över allt annat, men när hon flyttar till Stockholm och slutar höra av sig spränger hans faderskärlek allt förstånd. " +
                "Jan sjunker in i en drömvärld där dottern är kejsarinna av Portugallien och han är kejsare.");

        booksInLibrary.add(book1);
        booksInLibrary.add(book2);
        booksInLibrary.add(book3);
        booksInLibrary.add(book4);
        booksInLibrary.add(book5);
        booksInLibrary.add(book6);
        booksInLibrary.add(book7);
        booksInLibrary.add(book8);
        booksInLibrary.add(book9);
        booksInLibrary.add(book10);
        booksInLibrary.add(book11);
        booksInLibrary.add(book12);
        booksInLibrary.add(book13);
        booksInLibrary.add(book14);
        booksInLibrary.add(book15);
        booksInLibrary.add(book16);
        booksInLibrary.add(book17);
        booksInLibrary.add(book18);
        booksInLibrary.add(book19);
        booksInLibrary.add(book20);
        booksInLibrary.add(book21);
        booksInLibrary.add(book22);
        booksInLibrary.add(book23);
        booksInLibrary.add(book24);
        booksInLibrary.add(book25);
        booksInLibrary.add(book26);
        booksInLibrary.add(book27);
        booksInLibrary.add(book28);
        booksInLibrary.add(book29);
        booksInLibrary.add(book30);
        booksInLibrary.add(book31);
        booksInLibrary.add(book32);
        booksInLibrary.add(book33);
        booksInLibrary.add(book34);
        booksInLibrary.add(book35);
        booksInLibrary.add(book36);
        booksInLibrary.add(book37);
        booksInLibrary.add(book38);
        booksInLibrary.add(book39);
        booksInLibrary.add(book40);
    }

}
