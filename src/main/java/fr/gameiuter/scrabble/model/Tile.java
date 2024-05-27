package fr.gameiuter.scrabble.model;

import java.util.Objects;

public class Tile {
    public static Tile NO = null;

    private final char letter;
    private final int score;

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

    public int score() {
        return this.score;
    }

    public boolean isJoker() {
        return this.letter == '*';
    }

    @Override
    public String toString() {
        return "Tile(" + "letter=" + letter + ", score=" + score + ')';
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, letter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return score == tile.score && Objects.equals(letter, tile.letter);
    }
}
