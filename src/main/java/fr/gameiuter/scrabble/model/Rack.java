package fr.gameiuter.scrabble.model;

import java.util.ArrayList;
import java.util.List;

public class Rack {
    private final List<Tile> tiles;

    public Rack(){
        this.tiles = new ArrayList<>();
    }

    public void clear() {
        tiles.clear();
    }

    public void add(Tile tile) {
        tiles.add(tile);
    }

    public void remove(Tile tile) {
        tiles.remove(tile);
    }
    public String display(){
        StringBuilder builder = new StringBuilder();
        builder.append("\n┌───┬───┬───┬───┬───┬───┬───┐\n");

        for (int i = 0; i < 7; i++){
            builder.append("│ ");
            if (tiles.size() < i + 1) {
                builder.append(" ");
            } else {
                builder.append(tiles.get(i));
            }

            builder.append(" ");
        }

        builder.append("│\n");
        builder.append("└───┴───┴───┴───┴───┴───┴───┘");

        return builder.toString();
    }
}
