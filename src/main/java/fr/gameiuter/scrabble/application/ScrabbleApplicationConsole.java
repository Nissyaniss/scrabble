package fr.gameiuter.scrabble.application;

import fr.gameiuter.scrabble.controller.GameController;
import fr.gameiuter.scrabble.gui.Console;
import fr.gameiuter.scrabble.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ScrabbleApplicationConsole {
    private final GameController controller;

    private ScrabbleApplicationConsole(Player player) {
        this.controller = new GameController(player);
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
        this.controller.start();
        boolean stop = false;

        while (!stop) {
            Console.message("Votre score est de " + this.controller.player().score() + " points");
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
            }
        }
    }

    private void placeWord() {
        Board board = controller.board();
        Rack rack = controller.player().rack();
        HashMap<Position, Tile> word = new HashMap<>();
        Direction direction;
        Tile tile;

        Console.displayBoard(board);
        Console.displayRack(rack);

        while (true) {
            int choix;
            int end;
            int length;
            int lineeIfWordHorizontalElseColumn;
            int columnIfWordIVerticalElseLine;
            String direct1;
            String direct2;

            while (true) {
                Console.message("Quelle est la direction de votre mot:");
                Console.message("1. Horizontal");
                Console.message("2. Vertical");
                choix = Console.inputIntegerBetween("", 1, 2);

                if (choix == 1) {
                    direction = Direction.HORIZONTAL;
                    direct1 = "ligne";
                    direct2 = "colonne";
                } else {
                    direction = Direction.VERTICAL;
                    direct1 = "colonne";
                    direct2 = "ligne";
                }

                lineeIfWordHorizontalElseColumn = Console.inputIntegerBetween("Choisissez la " + direct1 + " de votre mot: ", 1, Board.SIZE) - 1;
                columnIfWordIVerticalElseLine = Console.inputIntegerBetween("Choisissez la " + direct2 + " de début du mot: ", 1, Board.SIZE) - 1;
                end = Console.inputIntegerBetween("Choisissez la " + direct2 + " de fin du mot: ", columnIfWordIVerticalElseLine + 1, Integer.min(columnIfWordIVerticalElseLine + 1 + rack.numberOfTiles(), Board.SIZE)) - 1;
                length = end - columnIfWordIVerticalElseLine;

                if (choix == 1 ? board.checkPlacement(length, new Position(columnIfWordIVerticalElseLine, lineeIfWordHorizontalElseColumn), direction) : board.checkPlacement(length, new Position(lineeIfWordHorizontalElseColumn, columnIfWordIVerticalElseLine), direction)) {
                    break;
                } else {
                    Console.message("Vous ne pouvez pas placer de mot à cet endroit");
                }
            }

            for (int i = columnIfWordIVerticalElseLine; i <= end; i++) {
                Position currentPosition = choix == 1 ? new Position(i, lineeIfWordHorizontalElseColumn) : new Position(lineeIfWordHorizontalElseColumn, i);
                if (board.hasTileAt(currentPosition))
                    continue;

                Console.message(Console.SEPARATOR);
                Console.message("Cette lettre sera placée à la " + direct2 + " " + (i + 1) + " et à la " + direct1 + " " + (lineeIfWordHorizontalElseColumn + 1));
                int indexLetter = Console.inputIntegerBetween("Rentrez l'indice de la lettre à déposer: ", 1, rack.numberOfTiles()) - 1;
                tile = rack.tileAt(indexLetter);
                Console.message(Console.SEPARATOR);
                if (tile.isJoker()) {
                    String result;
                    while (true) {
                        result = Console.input("Le jeton choisi est un joker, quelle lettre devrait-il être ? ");
                        result = result.toLowerCase();
                        if (result.length() == 1 && Character.isAlphabetic(result.charAt(0))) {
                            tile = new Tile(result.charAt(0), 0);
                            break;
                        } else {
                            Console.message("Ce que vous avez rentré n'est pas une lettre.");
                        }
                    }
                }
                word.put(currentPosition, tile);
                rack.remove(tile);
                Console.displayRack(rack);
            }

            if (word.isEmpty())
                Console.message("Il y a déjà un mot à cet endroit");
            else
                break;
        }


        int score = this.controller.computeScore(word, direction);
        this.controller.player().incrementScore(score);

        for (Map.Entry<Position, Tile> entry : word.entrySet()) {
            board.placeTile(entry.getValue(), entry.getKey().column(), entry.getKey().line());
        }
        Console.displayBoard(board);
        Console.displayRack(rack);
        Console.message("Cette action vous rajoute " + score + " points");

        this.controller.draw(controller.player());
    }

    private void swapLetters() {
        if (!this.controller.canSwap()) {
            Console.message("Action impossible, il n'y a pas assez de lettres dans le sac");
            return;
        }

        Player player = this.controller.player();
        List<Integer> indexes = new ArrayList<>();
        List<Tile> toExchange = new ArrayList<>();

        while (player.rack().numberOfTiles() > 0) {
            Console.message("Voici vos lettres:");
            if (!toExchange.isEmpty()) {
                Console.message("Les lettres suivantes vont être échangées: " + String.join(", ", toExchange.stream().map(tile -> tile.letter().toString()).toList()));
            }
            Console.displayRack(player.rack());
            int index = Console.inputIntegerBetween("Entrez l'indice de la lettre que vous souhaitez échanger (0 pour s'arrêter): ", 0, 8);
            if (index == 0) {
                break;
            } else if (indexes.contains(index)) { // checking the index because you can have the same letter multiple times
                Console.message("Cette lettre va déjà être échangée");
            } else {
                indexes.add(index);
                toExchange.add(player.rack().tileAt(index - 1));
            }
        }

        controller.swap(player, toExchange);
    }
}
