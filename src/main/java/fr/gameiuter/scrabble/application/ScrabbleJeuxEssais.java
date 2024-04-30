package fr.gameiuter.scrabble.application;

import fr.gameiuter.scrabble.model.Pouch;
import fr.gameiuter.scrabble.model.Rack;
import fr.gameiuter.scrabble.model.Tile;

import java.util.List;

public class ScrabbleJeuxEssais {
    public static void main(String[] args) {
        Pouch pouch = new Pouch();
        Rack rack = new Rack(pouch);

        System.out.println(rack.display());
        System.out.println(pouch);

        for (int i = 0; i < 7; i++) {
            rack.add(pouch.draw());
            System.out.println(rack.display());
            System.out.println(pouch);
        }

        List<Tile> list = rack.getList();
        rack.swap(list.get(6));
        System.out.println(rack.display());
        System.out.println(pouch);

        Tile tile;
        do {
            tile = pouch.draw();
            System.out.println(tile);
        } while (tile != null);
    }
}
