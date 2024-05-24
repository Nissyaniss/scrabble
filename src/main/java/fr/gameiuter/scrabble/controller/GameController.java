package fr.gameiuter.scrabble.controller;

import fr.gameiuter.scrabble.model.Board;
import fr.gameiuter.scrabble.model.Player;
import fr.gameiuter.scrabble.model.Pouch;
import fr.gameiuter.scrabble.model.Tile;

import java.util.Collection;
import java.util.Optional;

public class GameController {
    private final Board board;
    private final Pouch pouch;
    private final Player player;

    public GameController(Player player) {
        this.board = new Board();
        this.pouch = new Pouch();
        this.player = player;
    }

    public void start() {
        this.draw(this.player);
    }

    public void draw(Player player) {
        while (player.rack().numberOfTiles() < 7 && !pouch.isEmpty()) {
            Optional<Tile> tile = pouch.draw();
            player.rack().add(tile.get());
        }
    }

    public boolean canSwap() {
        return this.pouch.remainingTiles() >= 7;
    }

    public void swap(Player player, Collection<Tile> tiles) {
        for (Tile tile : tiles) {
            player.rack().remove(tile);
            this.pouch.putBack(tile);
        }
        this.draw(player);
    }

    public Pouch getPouch() {
        return this.pouch;
    }

    public Player player() {
        return this.player;
    }

    @Override
    public String toString() {
        return "GameController{" +
                "pouch=" + pouch +
                '}';
    }

    public Board board() {
        return this.board;
    }
}
