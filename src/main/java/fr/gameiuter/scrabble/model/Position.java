package fr.gameiuter.scrabble.model;

import java.util.Objects;

public class Position {
    private final Integer x;
    private final Integer y;

    public Position(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer x() {
        return x;
    }

    public Integer y() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(x, position.x) && Objects.equals(y, position.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
