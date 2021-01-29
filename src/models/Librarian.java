package models;
import java.io.*;
import java.util.Scanner;


public class Librarian extends User {

    private String adminID = "**";


    public Librarian(String name, String userID) {
        super(name, userID);

    }

    @Override
    public String getUserID() {
        return super.getUserID()+adminID;
    }

    @Override
    public String toString() {
        return "Librarian{" +
                "adminID='" + adminID + '\'' +
                '}';
    }

    public static void readFile() throws IOException{
        try{
            ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream("src/models/books.bin"));
            System.out.println(objectInput.readObject());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
