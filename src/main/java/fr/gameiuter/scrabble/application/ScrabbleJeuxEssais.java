package fr.gameiuter.scrabble.application;

import fr.gameiuter.scrabble.model.Pouch;
import fr.gameiuter.scrabble.model.Rack;

public class ScrabbleJeuxEssais {
    public static void main(String[] args) {
        Pouch pouch = new Pouch();
        Rack rack = new Rack();

        System.out.println(rack.display());

        for (int i = 0; i < 7; i++) {
            rack.add(pouch.draw());
        }

        System.out.println(rack.display());
    }
}
