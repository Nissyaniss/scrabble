package fr.gameiuter.scrabble.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final List<Square> squares;
    private List<Tile> placedTiles;

    public Board() {
        this.squares = new ArrayList<>();
    }

    public void placeTile(Tile tile) {
        // TODO
    }
}
