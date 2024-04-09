package fr.gameiuter.scrabble.model;

public enum Square {
    NORMAL,
    START;
    // This is for later
    // DOUBLE_LETTER,
    // TRIPLE_LETTER,
    // DOUBLE_WORD,
    // TRIPLE_WORD;

    public String symbol() {
        return switch (this) {
            case NORMAL -> " ";
            case START -> "*";
        };
    }
}
