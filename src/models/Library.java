package models;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 Skapa filhantering/
 läsa in böcker/
 lägga till böcker
 ta bort böcker
 */
public class Library {

    /*
    // Reads the book from txtfile and then outputs it
    public static void readBookFromTextFile(){
        try{
            File txtFile = new File("./src/models/books.txt");
            Scanner readTxtFile = new Scanner(txtFile);
            while(readTxtFile.hasNextLine()){
                String bookLine = readTxtFile.nextLine();
                System.out.println(bookLine);
            }
            readTxtFile.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

     */

    // Asks user for bookName, bookAuthor, bookGenre, bookIsbn and then add to txtFile
    public static void addBookManually() throws IOException {
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



        ObjectOutputStream objectOutStream = new ObjectOutputStream(new FileOutputStream("src/models/books.bin"));
        objectOutStream.writeObject(formattedBookInfo);

    }

    public static void addBookFromHashMap(Book value) throws IOException {
        ObjectOutputStream objectStreamOut = new ObjectOutputStream(new FileOutputStream("src/models/books.bin", true));
        objectStreamOut.writeObject(value);
        objectStreamOut.close();
    }
}
