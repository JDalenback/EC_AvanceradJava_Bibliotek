package models;

import java.io.File;
import java.util.Scanner;

/**
 Skapa filhantering/
 läsa in böcker/
 lägga till böcker
 ta bort böcker
 */
public class Library {

    // Reads the book from txtfile and then outputs it
    public static void readBookList(){
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




}
