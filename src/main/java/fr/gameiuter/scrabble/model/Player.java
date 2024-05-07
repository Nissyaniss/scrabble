package fr.gameiuter.scrabble.model;

public class Player {
    private final String name;
    private final Rack rack;

    public Player(String name) {
        this.name = name;
        this.rack = new Rack();
    }
    
    public String rackDisplay() {
        return this.rack.display();
    }

    public Rack getRack() {
        return this.rack;
    }
}
