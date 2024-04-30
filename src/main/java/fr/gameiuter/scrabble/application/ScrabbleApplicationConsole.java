package fr.gameiuter.scrabble.application;

import fr.gameiuter.scrabble.model.Board;
import fr.gameiuter.scrabble.model.Rack;

public class ScrabbleApplicationConsole {
    public static void main(String[] args) {
        System.out.println("-------------------------------------------------------");
        System.out.println("-- Bienvenue dans notre magnifique jeu de scrabble ! --");
        System.out.println("-- développé par Rayzeq                              --");
        System.out.println("-- développé par Nissyaniss                          --");
        System.out.println("-- développé par mdeguil                             --");
        System.out.println("-------------------------------------------------------");

        Board board = new Board();
        Rack rack = new Rack();
        System.out.println(board.display());
        System.out.println(rack.display());

    }
}
