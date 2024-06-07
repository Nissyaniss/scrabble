package fr.gameiuter.scrabble.model;

public class Tile {
    public static final Tile NO = null;
    private final int score;
    private char letter;

    public Tile(char letter, Integer score) {
        this.letter = letter;
        this.score = score;
    }

    public Tile(FrenchLetter letter) {
        this.letter = letter.letter();
        this.score = letter.score();
    }

    public Character letter() {
        return this.letter;
    }

    public void setLetter(Character letter) {
        this.letter = letter;
    }

    public int score() {
        return this.score;
    }

    public boolean isJoker() {
        return this.score == 0;
    }

    @Override
    public String toString() {
        return "Tile(" + "letter=" + letter + ", score=" + score + ')';
    }
}
