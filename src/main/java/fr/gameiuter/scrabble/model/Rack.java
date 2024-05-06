package fr.gameiuter.scrabble.model;

import java.util.ArrayList;
import java.util.List;

public class Rack {
    private final List<Tile> tiles;
    private final Pouch pouch;

    public Rack(Pouch pouch){
        this.tiles = new ArrayList<>();
        this.pouch = pouch;
    }

    public void clear() {
        tiles.clear();
    }

    public void add(Tile tile) {
        if (this.tiles.size() != 7) {
            tiles.add(tile);
        }
    }

    public void remove(Tile tile) {
        tiles.remove(tile);
    }
    
    public String display(){
        StringBuilder builder = new StringBuilder();
        builder.append("┌───┬───┬───┬───┬───┬───┬───┐\n");

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

    public void swap(Tile letter) {
        this.tiles.remove(letter);
        this.tiles.add(pouch.draw());
        this.pouch.putBack(letter);
    }

    public List<Tile> getList() {
        return this.tiles;
    }
}
