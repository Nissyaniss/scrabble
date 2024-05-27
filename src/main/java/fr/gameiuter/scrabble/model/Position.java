package fr.gameiuter.scrabble.model;

import java.util.Objects;

public class Position {
    private final Direction direction;
    private Integer column;
    private Integer line;

    public Position(Integer column, Integer line, Direction direction) {
        this.column = column;
        this.line = line;
        this.direction = direction;
    }

    public void previous() {
        if (this.direction.equals(Direction.HORIZONTAL)) {
            this.column -= 1;
        } else {
            this.line -= 1;
        }
    }

    public void next() {
        if (this.direction.equals(Direction.HORIZONTAL)) {
            this.column += 1;
        } else {
            this.line += 1;
        }
    }

    public Integer column() {
        return column;
    }

    public Integer line() {
        return line;
    }

    public boolean containedWithinBounds(int min, int max) {
        return this.column < min || this.column >= max || this.line < min || this.line >= max;
    }

    public Direction direction() {
        return direction;
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
