package fr.gameiuter.scrabble.model;

public class Player {
    private final String name;
    private final Rack rack;
    private int score;
    private int consecutiveSkippedTurns;

    public Player(String name) {
        this.name = name;
        this.rack = new Rack();
        this.score = 0;
        this.consecutiveSkippedTurns = 0;
    }

    public String name() {
        return name;
    }

    public Rack rack() {
        return this.rack;
    }

    public int score() {
        return this.score;
    }

    public void incrementScore(int score) {
        this.score += score;
    }

    public void skipTurn() {
        this.consecutiveSkippedTurns++;
    }

    public void normalPlay() {
        this.consecutiveSkippedTurns = 0;
    }

    public int consecutiveSkippedTurns() {
        return this.consecutiveSkippedTurns;
    }
}
