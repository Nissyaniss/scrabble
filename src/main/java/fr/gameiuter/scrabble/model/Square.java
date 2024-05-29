package fr.gameiuter.scrabble.model;

public enum Square {
    NORMAL(1, 1),
    START(1, 1),
    DOUBLE_LETTER(2, 1),
    TRIPLE_LETTER(3, 1),
    DOUBLE_WORD(1, 2),
    TRIPLE_WORD(1, 3);

    private final int letterMultiplier;
    private final int wordMultiplier;

    Square(int letterMultiplier, int wordMultiplier) {
        this.letterMultiplier = letterMultiplier;
        this.wordMultiplier = wordMultiplier;
    }

    public String symbol() {
        switch (this) {
            case NORMAL:
                return " ";
            case START:
                return "*";
            case DOUBLE_LETTER:
                return "2";
            case TRIPLE_LETTER:
                return "3";
            case DOUBLE_WORD:
                return "²";
            case TRIPLE_WORD:
                return "³";
        }
        return "Unreachable";
    }

    public int letterMultiplier() {
        return this.letterMultiplier;
    }

    public int wordMultiplier() {
        return this.wordMultiplier;
    }
}
