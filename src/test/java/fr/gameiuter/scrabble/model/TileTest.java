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
    void toStringRepresentation() {
        assertEquals("Tile(letter=a, score=1)", new Tile(FrenchLetter.A).toString());
    }

    @Test
    void differentTileIsNotEqual() {
        assertNotEquals(new Tile(FrenchLetter.A), new Tile(FrenchLetter.B));
    }
}
