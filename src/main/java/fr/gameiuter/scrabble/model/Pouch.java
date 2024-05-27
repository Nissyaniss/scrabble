package fr.gameiuter.scrabble.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Pouch {
    private final Random random = new Random();
    private final List<Tile> tiles;

    public Pouch() {
        this.tiles = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            this.tiles.add(Tile.E);
        }
        for (int i = 0; i < 9; i++) {
            this.tiles.add(Tile.A);
        }
        for (int i = 0; i < 8; i++) {
            this.tiles.add(Tile.I);
        }
        for (int i = 0; i < 6; i++) {
            this.tiles.add(Tile.N);
            this.tiles.add(Tile.O);
            this.tiles.add(Tile.R);
            this.tiles.add(Tile.S);
            this.tiles.add(Tile.T);
            this.tiles.add(Tile.U);
        }
        for (int i = 0; i < 5; i++) {
            this.tiles.add(Tile.L);
        }
        for (int i = 0; i < 3; i++) {
            this.tiles.add(Tile.D);
            this.tiles.add(Tile.M);
        }
        for (int i = 0; i < 2; i++) {
            this.tiles.add(Tile.G);
            this.tiles.add(Tile.B);
            this.tiles.add(Tile.C);
            this.tiles.add(Tile.P);
            this.tiles.add(Tile.F);
            this.tiles.add(Tile.H);
            this.tiles.add(Tile.V);
            // we need a new instance for each joker, because they're gonna be mutated
            this.tiles.add(Tile.blank());
        }
        this.tiles.add(Tile.J);
        this.tiles.add(Tile.Q);
        this.tiles.add(Tile.K);
        this.tiles.add(Tile.W);
        this.tiles.add(Tile.X);
        this.tiles.add(Tile.Y);
        this.tiles.add(Tile.Z);
    }

    public Optional<Tile> draw() {
        if (tiles.isEmpty())
            return Optional.empty();

        Tile result = tiles.get(this.random.nextInt(tiles.size()));
        tiles.remove(result);
        return Optional.of(result);
    }

    public void putBack(Tile tile) {
        tiles.add(tile);
    }

    public boolean isEmpty() {
        return this.tiles.isEmpty();
    }

    public int remainingTiles() {
        return this.tiles.size();
    }

    public List<Tile> tiles() {
        return tiles;
    }
}
