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
        for (FrenchLetter letter : FrenchLetter.values()) {
            for (int i = 0; i < letter.occurrences(); i++) {
                this.tiles.add(new Tile(letter));
            }
        }
    }

    public Optional<Tile> draw() {
        if (this.tiles.isEmpty())
            return Optional.empty();

        Tile result = this.tiles.get(this.random.nextInt(this.tiles.size()));
        this.tiles.remove(result);
        return Optional.of(result);
    }

    public void putBack(Tile tile) {
        this.tiles.add(tile);
    }

    public boolean isEmpty() {
        return this.tiles.isEmpty();
    }

    public int remainingTiles() {
        return this.tiles.size();
    }

    public List<Tile> tiles() {
        return this.tiles;
    }
}
