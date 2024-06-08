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

        result.ifPresent(tile -> pouch.putBack(tile));

        assertTrue(result.isPresent());
    }

    @Test
    void testDrawIsEmpty() {
        Optional<Tile> result = emptyPouch.draw();

        assertFalse(result.isPresent());
    }

    @Test
    void testPutBack() {
        Tile z = new Tile(FrenchLetter.Z);

        pouch.putBack(z);

        assertTrue(pouch.tiles().contains(z));
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
