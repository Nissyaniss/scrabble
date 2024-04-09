package fr.gameiuter.scrabble.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pouch {
    private List<Tile> pool;
    private Random random = new Random();

    public Pouch(){
        this.pool = new ArrayList<Tile>();
    }

    public Tile draw() {
        int size = pool.size();

        if (size == 0)
            return null;

        return pool.get(this.random.nextInt(size));
    }

    public void putBack(Tile tile) {
        pool.add(tile);
    }
}
