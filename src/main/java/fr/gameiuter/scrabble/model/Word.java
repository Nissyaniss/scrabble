package fr.gameiuter.scrabble.model;

import java.util.List;

public class Word {
    private Position position;
    private Direction direction;
    private List<Tile> tiles;

    public Word(Position position, Direction direction, List<Tile> tiles) {
        this.position = position;
        this.direction = direction;
        this.tiles = tiles;
    }

    public Position position() {
        return this.position;
    }

    public List<Tile> tiles() {
        return this.tiles;
    }

    @Override
    public String toString() {
        return "Word{" +
                "position=" + position +
                ", direction=" + direction +
                ", tiles=" + tiles +
                '}';
    }
}
