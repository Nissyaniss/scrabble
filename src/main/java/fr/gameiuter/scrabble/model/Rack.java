package fr.gameiuter.scrabble.model;

import java.util.ArrayList;
import java.util.List;

public class Rack {
    private List<Tile> tiles;

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
}
