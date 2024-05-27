package fr.gameiuter.scrabble.model;

import org.junit.jupiter.api.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @DisplayName("Tests for the score computing")
    @Nested
    class ScoreTest {
        private static Board board;
        private HashMap<Position, Tile> word;

        @BeforeAll
        static void initAll() {
            board = new Board();
            board.placeTile(new Tile(FrenchLetter.C), 6, 6);
            board.placeTile(new Tile(FrenchLetter.A), 6, 7);
            board.placeTile(new Tile(FrenchLetter.T), 6, 8);
            board.placeTile(new Tile(FrenchLetter.S), 6, 9);

            board.placeTile(new Tile(FrenchLetter.T), 11, 11);
            board.placeTile(new Tile(FrenchLetter.E), 12, 11);
            board.placeTile(new Tile(FrenchLetter.S), 13, 11);
            board.placeTile(new Tile(FrenchLetter.T), 14, 11);
        }

        @BeforeEach
        void init() {
            word = new HashMap<>();
        }

        @Test
        void basicCrossingWord() {
            word.put(new Position(5, 7), new Tile(FrenchLetter.B));
            word.put(new Position(7, 7), new Tile(FrenchLetter.T));

            // 3 + 1 + 1 = 5
            assertEquals(5, board.computeScore(word, Direction.HORIZONTAL));
        }

        @Test
        void basicAddingWord() {
            word.put(new Position(5, 10), new Tile(FrenchLetter.T));
            word.put(new Position(6, 10), new Tile(FrenchLetter.Z));

            // (1 + 10) + (3 + 1 + 1 + 1 + 10) = 27
            assertEquals(27, board.computeScore(word, Direction.HORIZONTAL));
        }

        @Test
        void withLetterMultiplier() {
            word.put(new Position(2, 5), new Tile(FrenchLetter.T));
            word.put(new Position(2, 6), new Tile(FrenchLetter.Z));

            // 1 + (10 * 2) = 21
            assertEquals(21, board.computeScore(word, Direction.VERTICAL));
        }

        @Test
        void withWordMultiplier() {
            word.put(new Position(1, 1), new Tile(FrenchLetter.T));
            word.put(new Position(1, 2), new Tile(FrenchLetter.Z));

            // (1 + 10) * 2 = 22
            assertEquals(22, board.computeScore(word, Direction.VERTICAL));
        }

        @Test
        void ignoreAlreadyUsedMultiplier() {
            // this would extend 'test'
            word.put(new Position(10, 11), new Tile(FrenchLetter.T));
            word.put(new Position(10, 12), new Tile(FrenchLetter.Z));

            // (1 + 10) + (1 + 1 + 1 + 1 + 1) = 16
            assertEquals(16, board.computeScore(word, Direction.VERTICAL));
        }

        @Test
        void verticalAtZero() {
            word.put(new Position(0, 1), new Tile(FrenchLetter.T));
            word.put(new Position(0, 2), new Tile(FrenchLetter.Z));

            // 1 + 10 = 11
            assertEquals(11, board.computeScore(word, Direction.VERTICAL));
        }

        @Test
        void verticalAtMax() {
            word.put(new Position(Board.LAST_LINE_OR_COLUMN, 1), new Tile(FrenchLetter.T));
            word.put(new Position(Board.LAST_LINE_OR_COLUMN, 2), new Tile(FrenchLetter.Z));

            // 1 + 10 = 11
            assertEquals(11, board.computeScore(word, Direction.VERTICAL));
        }

        @Test
        void horizontalAtBoundaries() {
            word.put(new Position(0, 3), new Tile(FrenchLetter.A)); // 1 * 2 = 2
            word.put(new Position(1, 3), new Tile(FrenchLetter.B)); // 3
            word.put(new Position(2, 3), new Tile(FrenchLetter.C)); // 3
            word.put(new Position(3, 3), new Tile(FrenchLetter.D)); // 2
            word.put(new Position(4, 3), new Tile(FrenchLetter.E)); // 1
            word.put(new Position(5, 3), new Tile(FrenchLetter.F)); // 4
            word.put(new Position(6, 3), new Tile(FrenchLetter.G)); // 2
            word.put(new Position(7, 3), new Tile(FrenchLetter.H)); // 4 * 2 = 8
            word.put(new Position(8, 3), new Tile(FrenchLetter.I)); // 1
            word.put(new Position(9, 3), new Tile(FrenchLetter.J)); // 8
            word.put(new Position(10, 3), new Tile(FrenchLetter.K)); // 10
            word.put(new Position(11, 3), new Tile(FrenchLetter.L)); // 1
            word.put(new Position(12, 3), new Tile(FrenchLetter.M)); // 2
            word.put(new Position(13, 3), new Tile(FrenchLetter.N)); // 1
            word.put(new Position(14, 3), new Tile(FrenchLetter.O)); // 1 * 2 = 2

            // 50 * 4 = 200
            assertEquals(200, board.computeScore(word, Direction.HORIZONTAL));
        }

        @Test
        void checkJokers() {
            word.put(new Position(0, 3), new Tile(FrenchLetter.A)); // 1 * 2 = 2
            word.put(new Position(1, 3), new Tile(FrenchLetter.B)); // 3
            word.put(new Position(2, 3), new Tile(FrenchLetter.C)); // 3
            word.put(new Position(3, 3), new Tile(FrenchLetter.D)); // 2
            word.put(new Position(4, 3), new Tile('*', 0)); // 0

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
            board.placeTile(new Tile(FrenchLetter.A), 7, 7);

            assertTrue(board.checkPlacement(4, 2, 7, Direction.HORIZONTAL));
        }

        @Test
        void testNormalMoveIsNotPossibleHorizontal() {
            board.placeTile(new Tile(FrenchLetter.B), 7, 7);

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
            board.placeTile(new Tile(FrenchLetter.A), 7, 7);

            assertTrue(board.checkPlacement(4, 7, 2, Direction.VERTICAL));
        }

        @Test
        void testNormalMoveIsNotPossibleVertical() {
            board.placeTile(new Tile(FrenchLetter.B), 7, 7);

            assertFalse(board.checkPlacement(5, 2, 1, Direction.VERTICAL));
        }

        // Normal moves with more words

        @Test
        void testCrossWords() {
            for (int i = 0; i < 15; i++) {
                board.placeTile(new Tile(FrenchLetter.A), i, 7);
            }

            assertTrue(board.checkPlacement(5, 7, 6, Direction.VERTICAL));
        }

        @Test
        void testMultipleCrossWords() {
            for (int i = 0; i < 15; i++) {
                board.placeTile(new Tile(FrenchLetter.A), i, 7);
            }

            assertTrue(board.checkPlacement(6, 7, 2, Direction.VERTICAL));

            for (int i = 2; i < 9; i++) {
                board.placeTile(new Tile(FrenchLetter.A), 7, i);
            }

            assertTrue(board.checkPlacement(8, 4, 4, Direction.HORIZONTAL));
        }
    }
}
