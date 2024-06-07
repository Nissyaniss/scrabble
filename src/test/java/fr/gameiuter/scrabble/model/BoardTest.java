package fr.gameiuter.scrabble.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;

    @BeforeEach
    void init() {
        board = new Board();
    }

    // Basic tests HORIZONTAL

    @Test
    void testFirstMoveIsPossibleHorizontal() {
        assertTrue(board.checkPlacement(5, new Position(6, 7), Direction.HORIZONTAL));
    }

    @Test
    void testFirstMoveIsNotPossibleHorizontal() {
        assertFalse(board.checkPlacement(0, new Position(6, 7), Direction.HORIZONTAL));
    }

    @Test
    void testNormalMoveIsPossibleHorizontal() {
        board.placeTile(new Tile(FrenchLetter.A), 7, 7);

        assertTrue(board.checkPlacement(4, new Position(2, 7), Direction.HORIZONTAL));
    }

    @Test
    void testNormalMoveIsNotPossibleHorizontal() {
        board.placeTile(new Tile(FrenchLetter.B), 7, 7);

        assertFalse(board.checkPlacement(5, new Position(2, 1), Direction.HORIZONTAL));
    }

    // Basic tests VERTICAL

    @Test
    void testFirstMoveIsPossibleVertical() {
        assertTrue(board.checkPlacement(5, new Position(7, 6), Direction.VERTICAL));
    }

    @Test
    void testFirstMoveIsNotPossibleVertical() {
        assertFalse(board.checkPlacement(0, new Position(6, 7), Direction.VERTICAL));
    }

    @Test
    void testNormalMoveIsPossibleVertical() {
        board.placeTile(new Tile(FrenchLetter.A), 7, 7);

        assertTrue(board.checkPlacement(4, new Position(7, 2), Direction.VERTICAL));
    }

    @Test
    void testNormalMoveIsNotPossibleVertical() {
        board.placeTile(new Tile(FrenchLetter.B), 7, 7);

        assertFalse(board.checkPlacement(5, new Position(2, 1), Direction.VERTICAL));
    }

    // Normal moves with more words

    @Test
    void testCrossWords() {
        for (int i = 0; i < 15; i++) {
            board.placeTile(new Tile(FrenchLetter.A), i, 7);
        }

        assertTrue(board.checkPlacement(5, new Position(7, 6), Direction.VERTICAL));
    }

    @Test
    void testMultipleCrossWords() {
        for (int i = 0; i < 15; i++) {
            board.placeTile(new Tile(FrenchLetter.A), i, 7);
        }

        assertTrue(board.checkPlacement(6, new Position(7, 2), Direction.VERTICAL));

        for (int i = 2; i < 9; i++) {
            board.placeTile(new Tile(FrenchLetter.A), 7, i);
        }

        assertTrue(board.checkPlacement(8, new Position(4, 4), Direction.HORIZONTAL));
    }

    // Tests for coverage

    @Test
    void testTilesGetter() {
        assertNotNull(board.tiles());
    }

    @Test
    void testSquaresGetter() {
        assertNotNull(board.squares());
    }
}
