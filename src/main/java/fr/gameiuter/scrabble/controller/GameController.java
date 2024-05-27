package fr.gameiuter.scrabble.controller;

import fr.gameiuter.scrabble.model.*;

import java.util.*;

public class GameController {
    private final Board board;
    private final Pouch pouch;
    private final Player player;

    public GameController(Player player) {
        this.board = new Board();
        this.pouch = new Pouch();
        this.player = player;
    }

    public void start() {
        this.draw(this.player);
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

    public Pouch getPouch() {
        return this.pouch;
    }

    public Player player() {
        return this.player;
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

    public Integer computeScore(Map<Position, Tile> placedTiles, Direction direction) {
        int score = 0;
        Direction perpendicular = direction.perpendicular();

        // the placed tiles all are on the same the line, and are all connected (possibly by tiles that are already on the board)
        // its means we can use any tile of the word and computeWordScore will find the first one
        Position tilePosition = placedTiles.keySet().iterator().next();
        score += this.computeWordScore(placedTiles, tilePosition, direction);

        for (Position position : placedTiles.keySet())
            score += this.computeWordScore(placedTiles, position, perpendicular);

        return score;
    }

    private Integer computeWordScore(Map<Position, Tile> placedTiles, Position position, Direction direction) {
        int tilesInWord = 0;
        int wordMultiplier = 1;
        int score = 0;
        Word word = this.board.getWordAt(position, direction, placedTiles);
        position = word.position();

        for (Tile tile : word.tiles()) {
            int tileMultiplier = 1;
            if (!this.board.hasTileAt(position)) {
                tileMultiplier = this.board.getSquareAt(position).letterMultiplier();
                wordMultiplier *= this.board.getSquareAt(position).wordMultiplier();
            }
            score += tile.score() * tileMultiplier;
            tilesInWord++;
            position = position.next(direction);
        }

        if (tilesInWord > 1)
            return score * wordMultiplier;
        else
            return 0;
    }
}
