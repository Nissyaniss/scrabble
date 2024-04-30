package fr.gameiuter.scrabble.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pouch {
    private final Random random = new Random();
    private final List<Tile> pool;

    public Pouch() {
        this.pool = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            this.pool.add(Tile.E);
        }
        for (int i = 0; i < 9; i++) {
            this.pool.add(Tile.A);
        }
        for (int i = 0; i < 8; i++) {
            this.pool.add(Tile.I);
        }
        for (int i = 0; i < 6; i++) {
            this.pool.add(Tile.N);
            this.pool.add(Tile.O);
            this.pool.add(Tile.R);
            this.pool.add(Tile.S);
            this.pool.add(Tile.T);
            this.pool.add(Tile.U);
        }
        for (int i = 0; i < 5; i++) {
            this.pool.add(Tile.L);
        }
        for (int i = 0; i < 3; i++) {
            this.pool.add(Tile.D);
            this.pool.add(Tile.M);
        }
        for (int i = 0; i < 2; i++) {
            this.pool.add(Tile.G);
            this.pool.add(Tile.B);
            this.pool.add(Tile.C);
            this.pool.add(Tile.P);
            this.pool.add(Tile.F);
            this.pool.add(Tile.H);
            this.pool.add(Tile.V);
            this.pool.add(Tile.BLANK);
        }
        this.pool.add(Tile.J);
        this.pool.add(Tile.Q);
        this.pool.add(Tile.K);
        this.pool.add(Tile.W);
        this.pool.add(Tile.X);
        this.pool.add(Tile.Y);
        this.pool.add(Tile.Z);
    }

    public Tile draw() {
        int size = pool.size();

        if (size == 0)
            return null;

        Tile result = pool.get(this.random.nextInt(size));
        pool.remove(result);
        return result;
    }

    public void putBack(Tile tile) {
        pool.add(tile);
    }

    @Override
    public String toString() {
        return this.pool.toString();
    }
}
