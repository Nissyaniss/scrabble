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
            assertEquals(5, board.computeScore(word, 5, 7, Direction.HORIZONTAL));
        }

        @Test
        void basicAddingWord() {
            word.put(new Coords(5, 10), Tile.T);
            word.put(new Coords(6, 10), Tile.Z);

            // (1 + 10) + (3 + 1 + 1 + 1 + 10) = 27
            assertEquals(27, board.computeScore(word, 5, 10, Direction.HORIZONTAL));
        }

        @Test
        void withLetterMultiplier() {
            word.put(new Coords(2, 5), Tile.T);
            word.put(new Coords(2, 6), Tile.Z);

            // 1 + (10 * 2) = 21
            assertEquals(21, board.computeScore(word, 2, 5, Direction.VERTICAL));
        }

        @Test
        void withWordMultiplier() {
            word.put(new Coords(1, 1), Tile.T);
            word.put(new Coords(1, 2), Tile.Z);

            // (1 + 10) * 2 = 22
            assertEquals(22, board.computeScore(word, 1, 1, Direction.VERTICAL));
        }

        @Test
        void verticalAtZero() {
            word.put(new Coords(0, 1), Tile.T);
            word.put(new Coords(0, 2), Tile.Z);

            // 1 + 10 = 11
            assertEquals(11, board.computeScore(word, 0, 1, Direction.VERTICAL));
        }

        @Test
        void verticalAtMax() {
            word.put(new Coords(Board.SIZE - 1, 1), Tile.T);
            word.put(new Coords(Board.SIZE - 1, 2), Tile.Z);

            // 1 + 10 = 11
            assertEquals(11, board.computeScore(word, Board.SIZE - 1, 1, Direction.VERTICAL));
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
            assertEquals(200, board.computeScore(word, 0, 3, Direction.HORIZONTAL));
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
