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
            case NORMAL, DOUBLE_LETTER, TRIPLE_LETTER, DOUBLE_WORD, TRIPLE_WORD -> " ";
            case START -> "*";
        };
    }
}
