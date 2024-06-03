package fr.gameiuter.scrabble.model;

import java.util.List;
import java.util.stream.Collectors;

public class Word {
    private final Position position;
    private final Direction direction;
    private final List<Tile> tiles;

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

    public Direction direction() {
        return this.direction;
    }

    public int letterCount() {
        return this.tiles.size();
    }

    public String asString() {
        return tiles.stream().map(tile -> tile.letter().toString()).collect(Collectors.joining(""));
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
