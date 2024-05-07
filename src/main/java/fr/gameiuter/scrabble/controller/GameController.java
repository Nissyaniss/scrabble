package fr.gameiuter.scrabble.controller;

import fr.gameiuter.scrabble.model.Player;
import fr.gameiuter.scrabble.model.Pouch;
import fr.gameiuter.scrabble.model.Tile;

public class GameController {
    private final Pouch pouch;

    public GameController() {
        this.pouch = new Pouch();
    }

    public void swap(Player player, Tile[] tiles) {
        for (Tile tile : tiles) {
            player.getRack().remove(tile);
            this.pouch.putBack(tile);
        }
        for (int i = 0; i < tiles.length; i++) {
            player.getRack().add(this.pouch.draw());
        }
    }

    public Pouch getPouch() {
        return this.pouch;
    }

    @Override
    public String toString() {
        return "GameController{" +
                "pouch=" + pouch +
                '}';
    }
}
