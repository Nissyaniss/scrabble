package fr.gameiuter.scrabble.model;

public enum Direction {
    HORIZONTAL,
    VERTICAL;

    public Direction perpendicular() {
        return this == Direction.HORIZONTAL ? Direction.VERTICAL : Direction.HORIZONTAL;
    }
}