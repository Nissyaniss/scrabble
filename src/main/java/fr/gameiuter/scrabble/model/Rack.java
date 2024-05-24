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



    public Tile tile(int index) {
        return tiles.get(index);
    }

    public List<Tile> tiles() {
        return this.tiles;
    }
}
