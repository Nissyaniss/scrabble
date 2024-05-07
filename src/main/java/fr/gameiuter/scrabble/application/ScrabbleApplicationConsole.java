package fr.gameiuter.scrabble.application;

import fr.gameiuter.scrabble.gui.Console;
import fr.gameiuter.scrabble.model.Board;
import fr.gameiuter.scrabble.model.Rack;

public class ScrabbleApplicationConsole {
    public static void main(String[] args) {
        Console.message(Console.SEPARATOR);
        Console.message("-- Bienvenue dans notre magnifique jeu de scrabble ! --");
        Console.message("--     développé par Rayzeq                          --");
        Console.message("--     et Nissyaniss                                 --");
        Console.message("--     et mdeguil                                    --");
        Console.message(Console.SEPARATOR);

        Board board = new Board();
        Rack rack = new Rack();
        Console.message(board.display());
        Console.message(rack.display());

    }
}
