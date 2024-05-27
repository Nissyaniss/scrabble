package fr.gameiuter.scrabble.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PouchTest {
    private static Pouch pouch;
    private static Pouch emptyPouch;

    @BeforeAll
    static void initAll() {
        PouchTest.pouch = new Pouch();
        PouchTest.emptyPouch = new Pouch();
        PouchTest.emptyPouch.tiles().clear();
    }

    @Test
    void testDraw() {
        Optional<Tile> result = pouch.draw();

        if (result.isPresent()) {
            pouch.putBack(result.get());
        }

        assertTrue(result.isPresent());
    }

    @Test
    void testDrawIsEmpty() {
        Optional<Tile> result = emptyPouch.draw();

        assertFalse(result.isPresent());
    }

    @Test
    void testPutBack() {
        Tile z = Tile.Z;
        int counter = 0;

        pouch.putBack(z);

        for (Tile tile : pouch.tiles()) {
            if (tile == z) {
                counter++;
            }
        }

        assertEquals(2, counter);
    }

    @Test
    void testIsEmpty() {
        assertTrue(emptyPouch.isEmpty());
    }

    @Test
    void testIsNotEmpty() {
        assertFalse(pouch.isEmpty());
    }

    @Test
    void testRemainingTiles() {
        assertEquals(0, emptyPouch.remainingTiles());
    }
}
