package models;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Library {
    private List<Book> booksInLibrary = new ArrayList<>();


    public Library() {
        readInBooks();
    }


    public void showAllBooksInLibrary(){
        System.out.println("All books in the library:");
        booksInLibrary.forEach(System.out::println);
    }

    public void searchForBook(String searchParameter){
        Optional<Book> searchResult = booksInLibrary
                .stream()
                .filter(book -> book.getTitle()
                        .equals(searchParameter) ||
                        book.getAuthor().equals(searchParameter) ||
                        book.getIsbn().equals(searchParameter))
                .findAny();

        if(searchResult.isPresent())
            System.out.println(searchResult.get());
        else
            System.out.println("Can't find that book in the library.");
    }





















    // To be removed when save/read file is implemented.
    public void readInBooks() {
        BookTracker bookTracker = new BookTracker();
        Book book1 = new Book("Vita tänder", "Zadie Smith", "9789175036434", "I en myllrande del av London möts medlemmar från familjerna Jones, Iqbal " +
                "och Chalfens. De har olika bakgrund, religion och hudfärg men deras liv vävs samman i en oförutsägbar berättelse. " +
                "Vänskapen mellan två omaka män, Archie Jones och Samad Iqba, går som en röd tråd genom romanen som spänner sig över ett halvt sekel.", bookTracker);
        Book book2 = new Book("Norwegian Wood", "Haruki Murakami", "9789113089461", "Boken som gjorde Haruki Murakami till en superstjärna i litteraturen. " +
                "När Toru av en slump träffar sin väns före detta flickvän Naoko blir han huvudlöst förälskad. Deras kärlek är lika delar öm, intensiv och omöjlig. Naoko är vacker men har" +
                " ett bräckligt psyke och medan hon försvinner in i vårdhem skriver Toru brev, gör korta besök och väntar. Läs boken för skildringarna av relationer, " +
                "kärlek, sex och känslomässigt beroende.", bookTracker);
        Book book3 = new Book("Återstoden av dagen", "Kazuo Ishiguro", "9789174297126", "2017 års mottagare av Nobelpriset i litteratur Kazuo Ishiguro ligger bakom denna " +
                "storsäljare. Butlern Stevens ger sig ut på sitt livs första semester i sin husbondes bil. Läsaren får följa med på en resa genom 1950-talets England såväl som genom Stevens " +
                "egna minnen.", bookTracker);
        Book book4 = new Book("Glaskupan", "Sylvia Plath", "9789174293418", "I en tävling vinner 19-åriga Esther en månads praktik på en tidskrift i New York. Hon har ett klarögt sätt" +
                " att se på världen och gör träffande beskrivningar av det hon upplever och människorna hon möter. Esther för dock en ständig kamp mot sin psykiska ohälsa. Boken väcker många frågor om " +
                "rollerna vi människor, och särskilt kvinnor, förväntas kliva in i — och vad som händer när vi misslyckas.", bookTracker);
        Book book5 = new Book("Ett år av magiskt tänkande", "Joan Didion", "9789173893091", "År 2003 ligger Joan och maken Johns enda dotter på sjukhus, svävande mellan liv och död. En kväll drabbas" +
                " John av en massiv hjärtinfarkt och dör. Ett år av magiskt tänkande är Joan Didions försök att förstå tiden som följde. En bok om sorg, mörker och liv skriven på ett rått och rakt sätt.", bookTracker);


        booksInLibrary.add(book1);
        booksInLibrary.add(book2);
        booksInLibrary.add(book3);
        booksInLibrary.add(book4);
        booksInLibrary.add(book5);
    }
}
