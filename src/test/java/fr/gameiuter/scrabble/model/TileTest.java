package fr.gameiuter.scrabble.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TileTest {
    @Test
    void blankTileAttributesAreCorrect() {
        Tile joker = Tile.blank();

        assertEquals('*', joker.letter());
        assertEquals(0, joker.score());
        assertTrue(joker.isJoker());
    }

    @Test
    void blankTileLettreCanBeChanged() {
        Tile joker = Tile.blank();

        joker.setLetter('a');

        assertEquals('a', joker.letter());
    }

    @Test
    void toStringRepresentation() {
        assertEquals("Tile(letter=a, score=1, isJoker=false)", Tile.A.toString());
    }

    @Test
    void differentTileIsNotEqual() {
        assertNotEquals(Tile.A, Tile.B);
    }
}
