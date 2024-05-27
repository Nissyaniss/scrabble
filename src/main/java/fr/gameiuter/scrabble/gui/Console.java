package fr.gameiuter.scrabble.gui;

import fr.gameiuter.scrabble.model.Board;
import fr.gameiuter.scrabble.model.Rack;

import java.util.Scanner;

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

    public static void displayBoard(Board board) {
        StringBuilder builder = new StringBuilder();
        builder.append("┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐\n");
        for (int line = 0; line < Board.LAST_LINE_OR_COLUMN; line++) {
            for (int column = 0; column < Board.SIZE; column++) {
                builder.append("│ ");
                if (board.tiles()[line][column] != null) {
                    builder.append(board.tiles()[line][column].letter());
                } else {
                    builder.append(board.squares()[line][column].symbol());
                }
                builder.append(" ");
            }
            builder.append("│ " + (line + 1) + "\n");
            builder.append("├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤\n");

        }

        for (int column = 0; column < Board.SIZE; column++) {
            builder.append("│ ");
            if (board.tiles()[Board.LAST_LINE_OR_COLUMN][column] != null) {
                builder.append(board.tiles()[Board.LAST_LINE_OR_COLUMN][column].letter());
            } else {
                builder.append(board.squares()[Board.LAST_LINE_OR_COLUMN][column].symbol());
            }
            builder.append(" ");
        }
        builder.append("│ 15\n");
        builder.append("└───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘\n");
        builder.append("  1   2   3   4   5   6   7   8   9   10  11  12  13  14  15");

        Console.message(builder.toString());
    }

    public static void displayRack(Rack rack) {
        StringBuilder builder = new StringBuilder();
        builder.append("┌───┬───┬───┬───┬───┬───┬───┐\n");

        for (int i = 0; i < Rack.SIZE; i++) {
            builder.append("│ ");
            if (rack.tiles().size() < i + 1) {
                builder.append(" ");
            } else {
                builder.append(rack.tiles().get(i).letter());
            }

            builder.append(" ");
        }

        builder.append("│\n");
        builder.append("└───┴───┴───┴───┴───┴───┴───┘\n");
        builder.append("  1   2   3   4   5   6   7");

        Console.message(builder.toString());
    }
}