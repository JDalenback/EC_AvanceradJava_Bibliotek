package models;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

/**
 Skapa filhantering/
 läsa in böcker/
 lägga till böcker
 ta bort böcker
 */
public class Library implements Serializable {

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
    public void addBookToHashMap(HashMap<String, Book> hashMap) {
        BookTracker bookTracker = new BookTracker();

        System.out.println("Which book do you want to add?");
        Scanner bookInput = new Scanner(System.in);
        String bookName = bookInput.nextLine();

        System.out.println("Which author does that book have?");
        Scanner authorInput = new Scanner(System.in);
        String bookAuthor = authorInput.nextLine();

        System.out.println("Which description does that book have?");
        Scanner genreInput = new Scanner(System.in);
        String bookDescription = genreInput.nextLine();

        System.out.println("Which ISBN does that book have?");
        Scanner IsbnInput = new Scanner(System.in);
        String bookIsbn = IsbnInput.nextLine();
        System.out.println("----------------------------");

        Book book1 = new Book(bookName, bookAuthor, bookIsbn, bookDescription, bookTracker);
        hashMap.put(bookIsbn, book1);
    }


    public static void serializeObject(Object library, String fileName){
        try{
            FileOutputStream fileOutStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutStream = new ObjectOutputStream(fileOutStream);
            objectOutStream.writeObject(library);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readBookFromFile() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream("src/models/books.ser"));

        Library library = (Library) objectInput.readObject();
        System.out.println(library);

    }
}
