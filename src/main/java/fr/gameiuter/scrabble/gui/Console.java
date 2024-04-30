package fr.gameiuter.scrabble.gui;

public class Console {
    public static final String SEPARATOR = "-------------------------------------------------------";

    public static void message(String text) {
        System.out.println(text);
    }

    public static void title(String text) {
        message(SEPARATOR);
        message(text);
        message(SEPARATOR);
    }
}
