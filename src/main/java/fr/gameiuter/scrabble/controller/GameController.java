package fr.gameiuter.scrabble.controller;

import fr.gameiuter.scrabble.application.ScrabbleApplicationJavaFX;
import fr.gameiuter.scrabble.gui.Console;
import fr.gameiuter.scrabble.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class GameController {
    private final Board board;
    private final Pouch pouch;
    private final HashSet<String> words;
    private final Player player1;
    private final Player player2;
    private int turn = 0;

    public GameController(Player player1, Player player2) {
        this.board = new Board();
        this.pouch = new Pouch();
        this.player1 = player1;
        this.player2 = player2;
        this.words = new HashSet<>();

        try {
            this.getWords();
        } catch (IOException e) {
            Console.message("Could not load word list");
            Console.message(e.getMessage());
        }
    }

    private void getWords() throws IOException {
        try (InputStream is = ScrabbleApplicationJavaFX.class.getResourceAsStream("/fr/gameiuter/scrabble/model/FrenchWords.txt")) {
            try (InputStreamReader isr = new InputStreamReader(is); BufferedReader reader = new BufferedReader(isr)) {
                for (Iterator<String> it = reader.lines().iterator(); it.hasNext(); ) {
                    String line = it.next();
                    this.words.add(line);
                }
            }
        }
    }

    public void start() {
        this.draw(this.player1);
        this.draw(this.player2);
    }

    public void draw(Player player) {
        while (player.rack().numberOfTiles() < 7 && !pouch.isEmpty()) {
            Optional<Tile> tile = pouch.draw();
            player.rack().add(tile.get());
        }
    }

    public boolean canSwap() {
        return this.pouch.remainingTiles() >= 7;
    }

    public void swap(Player player, Collection<Tile> tiles) {
        List<Tile> swappedTiles = new ArrayList<>();
        for (Tile tile : tiles) {
            player.rack().remove(tile);
            swappedTiles.add(tile);
        }
        this.draw(player);
        for (Tile tile : swappedTiles) {
            this.pouch.putBack(tile);
        }
    }

    public Player player(int turn) {
        if (turn % 2 == 0) {
            return this.player2;
        } else {
            return this.player1;
        }

    }

    @Override
    public String toString() {
        return "GameController{" +
                "pouch=" + pouch +
                '}';
    }

    public Board board() {
        return this.board;
    }

    public boolean isExistingWord(Word word) {
        return this.words.contains(word.asString().toUpperCase());
    }

    public Integer computeScore(Map<Position, Tile> placedTiles, Direction direction) {
        int score = 0;
        Direction perpendicular = direction.rotate();

        // the placed tiles all are on the same the line, and are all connected (possibly by tiles that are already on the board)
        // its means we can use any tile of the word and computeWordScore will find the first one
        Position tilePosition = placedTiles.keySet().iterator().next();
        score += this.computeWordScore(placedTiles, tilePosition, direction);

        for (Position position : placedTiles.keySet())
            score += this.computeWordScore(placedTiles, position, perpendicular);

        return score;
    }

    private Integer computeWordScore(Map<Position, Tile> placedTiles, Position position, Direction direction) {
        int wordMultiplier = 1;
        int score = 0;
        Word word = this.board.getWordAt(position, direction, placedTiles);
        if (word.letterCount() <= 1)
            return 0;

        position = word.position();

        for (Tile tile : word.tiles()) {
            int tileMultiplier = 1;
            if (!this.board.hasTileAt(position)) {
                tileMultiplier = this.board.getSquareAt(position).letterMultiplier();
                wordMultiplier *= this.board.getSquareAt(position).wordMultiplier();
            }
            score += tile.score() * tileMultiplier;
            position = position.next(direction);
        }

        return score * wordMultiplier;
    }

    public int getTurn() {
        return turn;
    }

    public void increaseTurn() {
        this.turn++;
    }
}
