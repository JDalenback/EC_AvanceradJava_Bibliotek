package models;
import java.io.*;
import java.util.Scanner;

public class Librarian extends User{



    public static void readFile() throws IOException{
        try{
            ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream("src/models/books.bin"));
            System.out.println(objectInput.readObject());
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
