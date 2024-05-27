package fr.gameiuter.scrabble.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {

    @Test
    void testIsString() {
        Position pos = new Position(1, 1);

        assertEquals("Position{column=1, line=1}", pos.toString());
    }

    @Test
    void testContainedWithinBounds() {
        Position pos = new Position(1, 1);
        assertTrue(pos.containedWithinBounds(1, 2));
    }

    @Test
    void testNotContainedWithinBounds() {
        Position pos = new Position(55, 302);
        assertFalse(pos.containedWithinBounds(1, 2));
    }
}
