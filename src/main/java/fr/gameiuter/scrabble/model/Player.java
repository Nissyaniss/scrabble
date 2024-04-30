package fr.gameiuter.scrabble.model;

public class Player {
    private final String name;
    private final Rack rack;
    private final MarkSheet markSheet;

    public Player(String name, Pouch pouch) {
        this.name = name;
        this.rack = new Rack(pouch);
        this.markSheet = new MarkSheet();
    }
}
