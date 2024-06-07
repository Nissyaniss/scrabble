package fr.gameiuter.scrabble.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SquareTest {

    @Test
    void testSymbol() {
        Square start = Square.START;
        assertEquals("*", start.symbol());

        Square normal = Square.NORMAL;
        assertEquals(" ", normal.symbol());

        Square doubleLetter = Square.DOUBLE_LETTER;
        assertEquals("2", doubleLetter.symbol());

        Square tripleLetter = Square.TRIPLE_LETTER;
        assertEquals("3", tripleLetter.symbol());

        Square doubleWord = Square.DOUBLE_WORD;
        assertEquals("²", doubleWord.symbol());

        Square tripleWord = Square.TRIPLE_WORD;
        assertEquals("³", tripleWord.symbol());
    }
}
