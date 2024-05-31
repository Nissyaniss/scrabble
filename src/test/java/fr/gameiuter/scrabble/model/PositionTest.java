package fr.gameiuter.scrabble.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {
    @Test
    void testIsString() {
        Position pos = new Position(1, 1);

        assertEquals("Position{column=1, line=1}", pos.toString());
    }

    @Test
    void testPreviousHorizontal() {
        Position pos = new Position(1, 1);

        assertEquals(new Position(0, 1), pos.previous(Direction.HORIZONTAL));
    }

    @Test
    void testNextHorizontal() {
        Position pos = new Position(1, 1);

        assertEquals(new Position(2, 1), pos.next(Direction.HORIZONTAL));
    }

    @Test
    void testPreviousVertical() {
        Position pos = new Position(1, 1);

        assertEquals(new Position(1, 0), pos.previous(Direction.VERTICAL));
    }

    @Test
    void testNextVertical() {
        Position pos = new Position(1, 1);

        assertEquals(new Position(1, 2), pos.next(Direction.VERTICAL));
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
