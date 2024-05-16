package fr.gameiuter.scrabble.model;

import org.junit.jupiter.api.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @DisplayName("Tests for the score computing")
    @Nested
    class ScoreTest {
        private static Board board;
        private HashMap<Coords, Tile> word;

        @BeforeAll
        static void initAll() {
            board = new Board();
            board.placeTile(Tile.C, 6, 6);
            board.placeTile(Tile.A, 6, 7);
            board.placeTile(Tile.T, 6, 8);
            board.placeTile(Tile.S, 6, 9);

            board.placeTile(Tile.T, 11, 11);
            board.placeTile(Tile.E, 12, 11);
            board.placeTile(Tile.S, 13, 11);
            board.placeTile(Tile.T, 14, 11);
        }

        @BeforeEach
        void init() {
            word = new HashMap<>();
        }

        @Test
        void basicCrossingWord() {
            word.put(new Coords(5, 7), Tile.B);
            word.put(new Coords(7, 7), Tile.T);

            // 3 + 1 + 1 = 5
            assertEquals(5, board.computeScore(word, Direction.HORIZONTAL));
        }

        @Test
        void basicAddingWord() {
            word.put(new Coords(5, 10), Tile.T);
            word.put(new Coords(6, 10), Tile.Z);

            // (1 + 10) + (3 + 1 + 1 + 1 + 10) = 27
            assertEquals(27, board.computeScore(word, Direction.HORIZONTAL));
        }

        @Test
        void withLetterMultiplier() {
            word.put(new Coords(2, 5), Tile.T);
            word.put(new Coords(2, 6), Tile.Z);

            // 1 + (10 * 2) = 21
            assertEquals(21, board.computeScore(word, Direction.VERTICAL));
        }

        @Test
        void withWordMultiplier() {
            word.put(new Coords(1, 1), Tile.T);
            word.put(new Coords(1, 2), Tile.Z);

            // (1 + 10) * 2 = 22
            assertEquals(22, board.computeScore(word, Direction.VERTICAL));
        }

        @Test
        void ignoreAlreadyUsedMultiplier() {
            // this would extend 'test'
            word.put(new Coords(10, 11), Tile.T);
            word.put(new Coords(10, 12), Tile.Z);

            // (1 + 10) + (1 + 1 + 1 + 1 + 1) = 16
            assertEquals(16, board.computeScore(word, Direction.VERTICAL));
        }

        @Test
        void verticalAtZero() {
            word.put(new Coords(0, 1), Tile.T);
            word.put(new Coords(0, 2), Tile.Z);

            // 1 + 10 = 11
            assertEquals(11, board.computeScore(word, Direction.VERTICAL));
        }

        @Test
        void verticalAtMax() {
            word.put(new Coords(Board.SIZE - 1, 1), Tile.T);
            word.put(new Coords(Board.SIZE - 1, 2), Tile.Z);

            // 1 + 10 = 11
            assertEquals(11, board.computeScore(word, Direction.VERTICAL));
        }

        @Test
        void horizontalAtBoundaries() {
            word.put(new Coords(0, 3), Tile.A); // 1 * 2 = 2
            word.put(new Coords(1, 3), Tile.B); // 3
            word.put(new Coords(2, 3), Tile.C); // 3
            word.put(new Coords(3, 3), Tile.D); // 2
            word.put(new Coords(4, 3), Tile.E); // 1
            word.put(new Coords(5, 3), Tile.F); // 4
            word.put(new Coords(6, 3), Tile.G); // 2
            word.put(new Coords(7, 3), Tile.H); // 4 * 2 = 8
            word.put(new Coords(8, 3), Tile.I); // 1
            word.put(new Coords(9, 3), Tile.J); // 8
            word.put(new Coords(10, 3), Tile.K); // 10
            word.put(new Coords(11, 3), Tile.L); // 1
            word.put(new Coords(12, 3), Tile.M); // 2
            word.put(new Coords(13, 3), Tile.N); // 1
            word.put(new Coords(14, 3), Tile.O); // 1 * 2 = 2

            // 50 * 4 = 200
            assertEquals(200, board.computeScore(word, Direction.HORIZONTAL));
        }

        @Test
        void checkJokers() {
            word.put(new Coords(0, 3), Tile.A); // 1 * 2 = 2
            word.put(new Coords(1, 3), Tile.B); // 3
            word.put(new Coords(2, 3), Tile.C); // 3
            word.put(new Coords(3, 3), Tile.D); // 2
            word.put(new Coords(4, 3), new Tile('A', 0, true)); // 0

            // 10 * 2 = 20
            assertEquals(20, board.computeScore(word, Direction.HORIZONTAL));
        }
    }

    @DisplayName("Tests for the checks of placement")
    @Nested
    class PlacementTest {
        private Board board;

        @BeforeEach
        void init() {
            board = new Board();
        }

        // Basic test

        @Test
        void testPlaceSamePlace() {
            board.placeTile(Tile.B, 7, 7);

            assertFalse(board.checkPlacement(0, 7, 7, Direction.HORIZONTAL));
        }

        // Basic tests HORIZONTAL

        @Test
        void testFirstMoveIsPossibleHorizontal() {
            assertTrue(board.checkPlacement(5, 6, 7, Direction.HORIZONTAL));
        }

        @Test
        void testFirstMoveIsNotPossibleHorizontal() {
            assertFalse(board.checkPlacement(0, 6, 7, Direction.HORIZONTAL));
        }

        @Test
        void testNormalMoveIsPossibleHorizontal() {
            board.placeTile(Tile.A, 7, 7);

            assertTrue(board.checkPlacement(4, 2, 7, Direction.HORIZONTAL));
        }

        @Test
        void testNormalMoveIsNotPossibleHorizontal() {
            board.placeTile(Tile.B, 7, 7);

            assertFalse(board.checkPlacement(5, 2, 1, Direction.HORIZONTAL));
        }

        // Basic tests VERTICAL

        @Test
        void testFirstMoveIsPossibleVertical() {
            assertTrue(board.checkPlacement(5, 7, 6, Direction.VERTICAL));
        }

        @Test
        void testFirstMoveIsNotPossibleVertical() {
            assertFalse(board.checkPlacement(0, 6, 7, Direction.VERTICAL));
        }

        @Test
        void testNormalMoveIsPossibleVertical() {
            board.placeTile(Tile.A, 7, 7);

            assertTrue(board.checkPlacement(4, 7, 2, Direction.VERTICAL));
        }

        @Test
        void testNormalMoveIsNotPossibleVertical() {
            board.placeTile(Tile.B, 7, 7);

            assertFalse(board.checkPlacement(5, 2, 1, Direction.VERTICAL));
        }

        // Normal moves with more words

        @Test
        void testCrossWords() {
            for (int i = 0; i < 15; i++) {
                board.placeTile(Tile.A, i, 7);
            }

            assertTrue(board.checkPlacement(5, 7, 6, Direction.VERTICAL));
        }

        @Test
        void testMultipleCrossWords() {
            for (int i = 0; i < 15; i++) {
                board.placeTile(Tile.A, i, 7);
            }

            assertTrue(board.checkPlacement(6, 7, 2, Direction.VERTICAL));

            for (int i = 2; i < 9; i++) {
                board.placeTile(Tile.A, 7, i);
            }

            assertTrue(board.checkPlacement(8, 4, 4, Direction.HORIZONTAL));
        }
    }
}
