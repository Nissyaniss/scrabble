package fr.gameiuter.scrabble.application;

import fr.gameiuter.scrabble.gui.Console;
import fr.gameiuter.scrabble.model.Player;
import fr.gameiuter.scrabble.model.Board;
import fr.gameiuter.scrabble.model.Direction;
import fr.gameiuter.scrabble.model.Rack;
import fr.gameiuter.scrabble.model.Tile;
import java.util.Scanner;


public class ScrabbleApplicationConsole {
    private Player player;

    private ScrabbleApplicationConsole(Player player) {
        this.player = player;
    }

    public static void main(String[] args) {
        Console.message(Console.SEPARATOR);
        Console.message("-- Bienvenue dans notre magnifique jeu de scrabble ! --");
        Console.message("--     développé par Rayzeq                          --");
        Console.message("--     et Nissyaniss                                 --");
        Console.message("--     et mdeguil                                    --");
        Console.message(Console.SEPARATOR);


        Player player = new Player(Console.input("Entrez votre pseudo: "));
        new ScrabbleApplicationConsole(player).start();
    }

    private void start() {
        boolean stop = false;

        while (!stop) {
            Console.message("Que souhaitez-vous faire ?");
            Console.message("  1. Placer un mot");
            Console.message("  2. Échanger des lettres");
            Console.message("  3. Quitter");
            int action = Console.inputIntegerBetween("Action souhaitée (1,2,3): ", 1, 3);

            switch (action) {
                case 1:
                    this.placeWord();
                    break;
                case 2:
                    this.swapLetters();
                    break;
                case 3:
                    stop = true;
                    break;
                default:
                    // unreachable
                    break;
            }
        }
    }

    private void placeWord() {
        
        Board board = new Board();
        Rack rack = new Rack();

        rack.add(Tile.M);
        rack.add(Tile.A);
        rack.add(Tile.I);
        rack.add(Tile.S);
        rack.add(Tile.O);
        rack.add(Tile.N);
        rack.add(Tile.BLANK);

        Console.message(board.display());
        Console.message(rack.display());

        Console.message("Tile : " + rack.getTile(4).letter());

        Scanner scanner = new Scanner(System.in);
        Console.message("Quel est la direction de votre mots :");
        Console.message("1. X : Horizontalement");
        Console.message("2. Y : Verticalement");
        int choix = scanner.nextInt();

        int y,x,max = 0;

        if (choix == 1)
        {
            Console.message("Choisisez la colonne de votre mots :");
            y = scanner.nextInt();
            Console.message("Choisisez la case du debut du mots :");
            x = scanner.nextInt();
            Console.message("Choisisez la case de la fin du mots :");
            max = scanner.nextInt() - x;

            for (int i = 0; i<max; i++){

                int xMots = x + i;

                Console.message("X : " +  xMots);
                Console.message("Y : " + y);
                Console.message(("-------------"));
                Console.message("Rentrer l'index de la lettre à deposer");
                int indexLetter = scanner.nextInt();
                Tile letter = rack.getTile(indexLetter);
                Console.message(("-------------"));

                board.placeTile(letter, xMots,y);
                rack.remove(letter);
                Console.message(rack.display());
            }
        }
        else
        {
            Console.message("Choisisez la ligne de votre mots : ");
            x = scanner.nextInt();
            Console.message("Choisisez la case du debut du mots :");
            y = scanner.nextInt();
            Console.message("Choisisez la case du debut du mots :");
            max = scanner.nextInt() - y;

            for (int i = 0; i<max; i++){

                int yMots = y + i;

                Console.message("X : " + x);
                Console.message("Y : " + yMots);
                Console.message(("-------------"));
                Console.message("Rentrer l'index de la lettre à deposer");
                int indexLetter = scanner.nextInt();
                Tile letter = rack.getTile(indexLetter);
                Console.message(("-------------"));

                board.placeTile(letter,x,yMots);
                rack.remove(letter);
                Console.message(rack.display());
            }
        }

        Console.message(board.display());
        Console.message(rack.display());
    }



    private void swapLetters() {
    }

}
