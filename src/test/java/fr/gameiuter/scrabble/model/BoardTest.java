package fr.gameiuter.scrabble.model;

import org.junit.jupiter.api.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @DisplayName("Tests for the score computing")
    @Nested
    class ScoreTest {

    }

    @DisplayName("Tests for the checks of placement")
    @Nested
    class PlacementTest {
        private Board board;

        @BeforeEach
        void init() {
            board = new Board();
        }

        @Test
        void testFirstMoveIsPossible() {
            assertTrue(board.checkPlacement(5, 6, 7, Direction.HORIZONTAL));
        }

        @Test
        void testFirstMoveIsNotPossible() {
            assertFalse(board.checkPlacement(0, 6, 7, Direction.HORIZONTAL));
        }

        @Test
        void testNormalMoveIsPossible() {
            board.placeTile(Tile.A, 7, 7);

            assertTrue(board.checkPlacement(4, 2, 7, Direction.HORIZONTAL));
        }

        @Test
        void testNormalMoveIsNotPossible() {
            board.placeTile(Tile.B, 7, 7);

            assertFalse(board.checkPlacement(5, 2, 1, Direction.HORIZONTAL));
        }

        //VERTICAL
        //more words
        //check vertical+horizontal
    }
}
