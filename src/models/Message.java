package models;

public class Message {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public static void  showMessage(String message, String color){
        System.out.println(getColor(color) + message + ANSI_RESET);
    }

    private static String getColor(String color){
        switch(color){
            case "red":
                return ANSI_RED;
            case "green":
                return ANSI_GREEN;
            case "yellow":
                return ANSI_YELLOW;
            default:
                return ANSI_RESET;
        }
    }

}
