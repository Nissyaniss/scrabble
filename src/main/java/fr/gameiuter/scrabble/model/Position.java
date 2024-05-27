package fr.gameiuter.scrabble.model;

import java.util.Objects;

public class Position {
    private final Integer column;
    private final Integer line;

    public Position(Integer column, Integer line) {
        this.column = column;
        this.line = line;
    }

    public Integer column() {
        return column;
    }

    public Integer line() {
        return line;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(column, position.column) && Objects.equals(line, position.line);
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, line);
    }
}
