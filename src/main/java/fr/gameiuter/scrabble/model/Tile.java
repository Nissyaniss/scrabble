package fr.gameiuter.scrabble.model;

import java.util.Objects;

public class Tile {
    public static final Tile A = new Tile('a', 1);
    public static final Tile B = new Tile('b', 3);
    public static final Tile C = new Tile('c', 3);
    public static final Tile D = new Tile('d', 2);
    public static final Tile E = new Tile('e', 1);
    public static final Tile F = new Tile('f', 4);
    public static final Tile G = new Tile('g', 2);
    public static final Tile H = new Tile('h', 4);
    public static final Tile I = new Tile('i', 1);
    public static final Tile J = new Tile('j', 8);
    public static final Tile K = new Tile('k', 10);
    public static final Tile L = new Tile('l', 1);
    public static final Tile M = new Tile('m', 2);
    public static final Tile N = new Tile('n', 1);
    public static final Tile O = new Tile('o', 1);
    public static final Tile P = new Tile('p', 3);
    public static final Tile Q = new Tile('q', 8);
    public static final Tile R = new Tile('r', 1);
    public static final Tile S = new Tile('s', 1);
    public static final Tile T = new Tile('t', 1);
    public static final Tile U = new Tile('u', 1);
    public static final Tile V = new Tile('v', 4);
    public static final Tile W = new Tile('w', 10);
    public static final Tile X = new Tile('x', 10);
    public static final Tile Y = new Tile('y', 10);
    public static final Tile Z = new Tile('z', 10);
    public static final Tile BLANK = new Tile('*', 0, true);

    private final int score;
    private final boolean isJoker;
    private char letter;

    private Tile(char letter, Integer score, boolean isJoker) {
        this.letter = letter;
        this.score = score;
        this.isJoker = isJoker;
    }

    private Tile(char letter, Integer score) {
        this(letter, score, false);
    }

    public Character letter() {
        return this.letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public int score() {
        return this.score;
    }

    public boolean isJoker() {
        return this.isJoker;
    }

    @Override
    public String toString() {
        return "Tile(" + "letter=" + letter + ", score=" + score + ", isJoker=" + isJoker + ')';
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, isJoker, letter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return score == tile.score && Objects.equals(letter, tile.letter);
    }
}
