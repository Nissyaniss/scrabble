package fr.gameiuter.scrabble.gui;

import java.util.Scanner;

public class Console {
    public static final String SEPARATOR = "-------------------------------------------------------";

    private Console() {
    }

    public static void message(String text) {
        System.out.println(text);
    }

    public static void title(String text) {
        message(SEPARATOR);
        message(text);
        message(SEPARATOR);
    }

    public static String input(String message) {
        System.out.print(message);
        return new Scanner(System.in).nextLine();
    }

    public static int inputInteger(String message) {
        String value = Console.input(message);
        do {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                value = Console.input("Veuillez entrer un nombre valide: ");
            }
        } while (true);
    }

    public static int inputIntegerBetween(String message, int min, int max) {
        int value = Console.inputInteger(message);
        while (value < min || value > max) {
            value = Console.inputInteger("Veuillez entrer un nombre entre " + min + " et " + max + ": ");
        }
        return value;
    }
}