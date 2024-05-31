package fr.gameiuter.scrabble.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class RackTest {
    @Test
    void newRackIsEmpty() {
        Rack rack = new Rack();

        assertEquals(0, rack.numberOfTiles());
    }

    @Test
    void canAddTileWhenEmpty() {
        Rack rack = new Rack();

        boolean wasAdded = rack.add(new Tile(FrenchLetter.A));

        assertTrue(wasAdded);
        assertEquals(1, rack.numberOfTiles());
        assertEquals(Collections.singletonList(new Tile(FrenchLetter.A)), rack.tiles());
    }

    @Test
    void cantAddTileWhenFull() {
        Rack rack = new Rack();
        for (int i = 0; i < Rack.SIZE; i++)
            rack.add(new Tile(FrenchLetter.A));

        boolean wasAdded = rack.add(new Tile(FrenchLetter.A));

        assertFalse(wasAdded);
        assertEquals(7, rack.numberOfTiles());
        assertEquals(
                Arrays.asList(
                        new Tile(FrenchLetter.A),
                        new Tile(FrenchLetter.A),
                        new Tile(FrenchLetter.A),
                        new Tile(FrenchLetter.A),
                        new Tile(FrenchLetter.A),
                        new Tile(FrenchLetter.A),
                        new Tile(FrenchLetter.A)
                ),
                rack.tiles()
        );
    }

    @Test
    void removeTile() {
        Rack rack = new Rack();
        rack.add(new Tile(FrenchLetter.A));

        rack.remove(new Tile(FrenchLetter.A));

        assertEquals(0, rack.numberOfTiles());
    }

    @Test
    void getTileAtIndexReturnTileWhenPresent() {
        Rack rack = new Rack();
        rack.add(new Tile(FrenchLetter.A));
        rack.add(new Tile(FrenchLetter.B));
        rack.add(new Tile(FrenchLetter.C));

        Tile tile = rack.tileAt(2);

        assertEquals(new Tile(FrenchLetter.C), tile);
    }

    @Test
    void getTileAtIndexRaiseExceptionWhenNotPresent() {
        Rack rack = new Rack();
        rack.add(new Tile(FrenchLetter.A));
        rack.add(new Tile(FrenchLetter.B));
        rack.add(new Tile(FrenchLetter.C));

        assertThrows(IndexOutOfBoundsException.class, () -> rack.tileAt(4));
    }
}
