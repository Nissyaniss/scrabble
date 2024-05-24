package fr.gameiuter.scrabble.model;

import java.util.ArrayList;
import java.util.List;

public class Rack {
    public static final Integer SIZE = 7;

    private final List<Tile> tiles;

    public Rack() {
        this.tiles = new ArrayList<>();
    }

    public int numberOfTiles() {
        return this.tiles.size();
    }

    public void add(Tile tile) {
        if (this.tiles.size() != SIZE) {
            tiles.add(tile);
        }
    }

    public void remove(Tile tile) {
        tiles.remove(tile);
    }

    public String display() {
        StringBuilder builder = new StringBuilder();
        builder.append("┌───┬───┬───┬───┬───┬───┬───┐\n");

        for (int i = 0; i < SIZE; i++) {
            builder.append("│ ");
            if (tiles.size() < i + 1) {
                builder.append(" ");
            } else {
                builder.append(tiles.get(i).letter());
            }

            builder.append(" ");
        }

        builder.append("│\n");
        builder.append("└───┴───┴───┴───┴───┴───┴───┘\n");
        builder.append("  1   2   3   4   5   6   7");

        return builder.toString();
    }

    public Tile getTile(int index) {
        return tiles.get(index);
    }

    public List<Tile> tiles() {
        return this.tiles;
    }
}
