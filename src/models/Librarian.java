package models;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Librarian extends User{

    // Asks user for bookName, bookAuthor, bookGenre, bookIsbn and then add to txtFile
    public static void addBook(){
        System.out.println("Which book do you want to add?");
        Scanner bookInput = new Scanner(System.in);
        String bookName = bookInput.nextLine();

        System.out.println("Which author does that book have?");
        Scanner authorInput = new Scanner(System.in);
        String bookAuthor = authorInput.nextLine();

        System.out.println("Which genre does that book have?");
        Scanner genreInput = new Scanner(System.in);
        String bookGenre = genreInput.nextLine();

        System.out.println("Which ISBN does that book have?");
        Scanner IsbnInput = new Scanner(System.in);
        String bookIsbn = IsbnInput.nextLine();
        System.out.println("----------------------------");

        String formattedBookInfo = String.format("%s, %s, %s, %s",
                bookName,
                bookAuthor,
                bookGenre,
                bookIsbn);

        try{
            FileWriter writer = new FileWriter("./src/models/books.txt", true);
            writer.write("\n" + formattedBookInfo);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
