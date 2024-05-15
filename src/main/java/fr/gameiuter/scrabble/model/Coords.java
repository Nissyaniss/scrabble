package fr.gameiuter.scrabble.model;

import java.util.Objects;

public class Coords {
    private Integer x;
    private Integer y;

    public Coords(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coords coords = (Coords) o;
        return Objects.equals(x, coords.x) && Objects.equals(y, coords.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
