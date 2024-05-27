package fr.gameiuter.scrabble.model;

import java.util.Objects;

public class Position {
    private Integer column;
    private Integer line;

    public Position(Integer column, Integer line) {
        this.column = column;
        this.line = line;
    }

    public Position previous(Direction direction) {
        if (direction.equals(Direction.HORIZONTAL)) {
            return new Position(this.column - 1, this.line);
        } else {
            return new Position(this.column, this.line - 1);
        }
    }

    public Position next(Direction direction) {
        if (direction.equals(Direction.HORIZONTAL)) {
            return new Position(this.column + 1, this.line);
        } else {
            return new Position(this.column, this.line + 1);
        }
    }

    public Integer column() {
        return column;
    }

    public Integer line() {
        return line;
    }

    public boolean containedWithinBounds(int min, int max) {
        return min <= this.column && this.column <= max && min <= this.line && this.line <= max;
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

    @Override
    public String toString() {
        return "Position{" +
                "column=" + column +
                ", line=" + line +
                '}';
    }
}
