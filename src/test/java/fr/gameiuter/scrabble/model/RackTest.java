package fr.gameiuter.scrabble.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

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

        boolean wasAdded = rack.add(Tile.A);

        assertTrue(wasAdded);
        assertEquals(1, rack.numberOfTiles());
        assertEquals(List.of(Tile.A), rack.tiles());
    }

    @Test
    void cantAddTileWhenFull() {
        Rack rack = new Rack();
        for (int i = 0; i < Rack.SIZE; i++)
            rack.add(Tile.A);

        boolean wasAdded = rack.add(Tile.A);

        assertFalse(wasAdded);
        assertEquals(7, rack.numberOfTiles());
        assertEquals(Arrays.asList(Tile.A, Tile.A, Tile.A, Tile.A, Tile.A, Tile.A, Tile.A), rack.tiles());
    }

    @Test
    void removeTile() {
        Rack rack = new Rack();
        rack.add(Tile.A);

        rack.remove(Tile.A);

        assertEquals(0, rack.numberOfTiles());
    }

    @Test
    void getTileAtIndexReturnTileWhenPresent() {
        Rack rack = new Rack();
        rack.add(Tile.A);
        rack.add(Tile.B);
        rack.add(Tile.C);

        Tile tile = rack.tileAt(2);

        assertEquals(Tile.C, tile);
    }

    @Test
    void getTileAtIndexRaiseExceptionWhenNotPresent() {
        Rack rack = new Rack();
        rack.add(Tile.A);
        rack.add(Tile.B);
        rack.add(Tile.C);

        assertThrows(IndexOutOfBoundsException.class, () -> rack.tileAt(4));
    }
}
