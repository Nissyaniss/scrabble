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
                default:
                    // unreachable
                    break;
            }
        }
    }

    private void placeWord() {
        Board board = controller.getBoard();
        Rack rack = controller.player().getRack();
        HashMap<Coords, Tile> word = new HashMap<>();
        Direction direction;
        Tile letter;

        Console.message(board.display());
        Console.message(rack.display());

        Console.message("Quelle est la direction de votre mot:");
        Console.message("1. Horizontal");
        Console.message("2. Vertical");
        int choix = Console.inputIntegerBetween("", 1, 2);

        int x;
        int y;
        int end;
        int length;
        if (choix == 1) {
            direction = Direction.HORIZONTAL;

            while (true) {
                y = Console.inputIntegerBetween("Choisissez la ligne de votre mot: ", 1, Board.SIZE) - 1;
                x = Console.inputIntegerBetween("Choisissez la colonne de début du mot: ", 1, Board.SIZE) - 1;
                end = Console.inputIntegerBetween("Choisissez la colonne de fin du mot: ", x, Integer.min(x + rack.numberOfTiles(), Board.SIZE)) - 1;
                length = end - x;
                for (int i = x; i <= end; i++) {
                    Console.message("X : " + i);
                    Console.message("Y : " + y);
                    Console.message(Console.SEPARATOR);
                    int indexLetter = Console.inputIntegerBetween("Rentrez l'indice de la lettre à déposer: ", 1, rack.numberOfTiles()) - 1;
                    letter = rack.getTile(indexLetter);
                    Console.message(Console.SEPARATOR);
                    if (letter.isJoker()) {
                        String result;
                        while (true) {
                            result = Console.input("Le jeton choisie est un joker, quelle lettre devrait il être ? : ");
                            result = result.toLowerCase();
                            if (result.length() == 1 && Character.isAlphabetic(result.charAt(0))) {
                                letter.setLetter(result.charAt(0));
                                break;
                            } else {
                                Console.message("Ce que vous avez rentré n'est pas une lettre.");
                            }
                        }
                    }
                    word.put(new Coords(i, y), letter);
                    rack.remove(letter);
                    Console.message(rack.display());
                }
                if (board.checkPlacement(length, x, y, direction)) {
                    break;
                } else {
                    for (Map.Entry<Coords, Tile> entry : word.entrySet()) {
                        rack.add(entry.getValue());
                    }
                    Console.message(board.display());
                    word.clear();
                }
            }
        } else {
            direction = Direction.VERTICAL;

            while (true) {
                x = Console.inputIntegerBetween("Choisissez la colonne de votre mot: ", 1, Board.SIZE) - 1;
                y = Console.inputIntegerBetween("Choisissez la ligne de début du mot: ", 1, Board.SIZE) - 1;
                end = Console.inputIntegerBetween("Choisissez la ligne de fin du mot: ", x, Integer.min(x + rack.numberOfTiles(), Board.SIZE)) - 1;
                length = end - y;
                for (int i = y; i <= end; i++) {
                    Console.message("X : " + x);
                    Console.message("Y : " + i);
                    Console.message(Console.SEPARATOR);
                    int indexLetter = Console.inputIntegerBetween("Rentrez l'indice de la lettre à déposer: ", 1, rack.numberOfTiles()) - 1;
                    letter = rack.getTile(indexLetter);
                    Console.message(Console.SEPARATOR);

                    if (letter.isJoker()) {
                        String result;
                        while (true) {
                            result = Console.input("Le jeton choisie est un joker, quelle lettre devrait il être ? : ");
                            result = result.toLowerCase();
                            if (result.length() == 1 && Character.isAlphabetic(result.charAt(0))) {
                                letter.setLetter(result.charAt(0));
                                break;
                            } else {
                                Console.message("Ce que vous avez rentré n'est pas une lettre.");
                            }
                        }
                    }
                    word.put(new Coords(x, i), letter);
                    rack.remove(letter);
                    Console.message(rack.display());
                }
                if (Boolean.TRUE.equals(board.checkPlacement(length, x, y, direction))) {
                    break;
                } else {
                    for (Map.Entry<Coords, Tile> entry : word.entrySet()) {
                        rack.add(entry.getValue());
                    }
                    Console.message(board.display());
                    word.clear();
                }
            }
        }

        int score = board.computeScore(word, x, y, direction);
        this.controller.player().incrementScore(score);

        for (Map.Entry<Coords, Tile> entry : word.entrySet()) {
            board.placeTile(entry.getValue(), entry.getKey().getX(), entry.getKey().getY());
        }
        Console.message(board.display());
        Console.message(rack.display());
        Console.message("Cette action vous rajoute " + score + " points");
    }

    private void swapLetters() {
        if (!this.controller.canSwap()) {
            Console.message("Action impossible, il n'y a pas assez de lettres dans le sac");
            return;
        }

        Player player = this.controller.player();
        List<Integer> indexes = new ArrayList<>();
        List<Tile> toExchange = new ArrayList<>();

        while (player.getRack().numberOfTiles() > 0) {
            Console.message("Voici vos lettres:");
            if (!toExchange.isEmpty()) {
                Console.message("Les lettres suivantes vont être échangées: " + String.join(", ", toExchange.stream().map(tile -> tile.letter().toString()).toList()));
            }
            Console.message(this.controller.player().rackDisplay());
            int index = Console.inputIntegerBetween("Entrez l'indice de la lettre que vous souhaitez échanger (0 pour s'arrêter): ", 0, 8);
            if (index == 0) {
                break;
            } else if (indexes.contains(index)) { // checking the index because you can have the same letter multiple times
                Console.message("Cette lettre va déjà être échangée");
            } else {
                indexes.add(index);
                toExchange.add(player.getRack().getTile(index - 1));
            }
        }

        controller.swap(player, toExchange);
    }
}
