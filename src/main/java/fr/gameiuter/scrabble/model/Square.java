package fr.gameiuter.scrabble.model;

public enum Square {
    NORMAL,
    START,
    DOUBLE_LETTER,
    TRIPLE_LETTER,
    DOUBLE_WORD,
    TRIPLE_WORD;

    public String symbol() {
        return switch (this) {
            case NORMAL -> " ";
            case START -> "*";
            case DOUBLE_LETTER -> "2";
            case TRIPLE_LETTER -> "3";
            case DOUBLE_WORD -> "²";
            case TRIPLE_WORD -> "³";
        };
    }
}
