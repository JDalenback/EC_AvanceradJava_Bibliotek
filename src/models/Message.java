package models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class Message {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public static void messageWithColor(String message, String color) {
        System.out.println(getColor(color) + message + ANSI_RESET);
    }

    public static void systemMessage(List<?> list) {
        System.out.println("\t\t----------------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("\t\t" + (i + 1) + ".\t" + list.get(i));
        }
        System.out.println("\t\t----------------------------------------------------------------------------------------------------------------------");
    }

    public static void systemMessage(Object object) {
        if (object != null) {
            System.out.println("\t\t----------------------------------------------------------------------------------------------------------------------");
            System.out.println("\t\t" + object);
            System.out.println("\t\t----------------------------------------------------------------------------------------------------------------------");
        } else {
            System.out.println("\t\t----------------------------------------------------------------------------------------------------------------------");
            messageWithColor("\t\tDoesn't exist, please try again. ", "red");
            System.out.println("\t\t----------------------------------------------------------------------------------------------------------------------");
        }
    }

    public static void systemMessage(String message) {
        System.out.println("\t\t----------------------------------------------------------------------------------------------------------------------");
        System.out.println("\t\t" + message);
        System.out.println("\t\t----------------------------------------------------------------------------------------------------------------------");
    }

    public static void systemMessageWithColor(String message, String color) {
        System.out.println("\t\t----------------------------------------------------------------------------------------------------------------------");
        messageWithColor("\t\t" + message, color);
        System.out.println("\t\t----------------------------------------------------------------------------------------------------------------------");
    }




    private static String getColor(String color) {
        switch (color) {
            case "red": return ANSI_RED;
            case "green": return ANSI_GREEN;
            case "yellow": return ANSI_YELLOW;
            case "blue": return ANSI_BLUE;
            default: return ANSI_RESET;
        }
    }

}