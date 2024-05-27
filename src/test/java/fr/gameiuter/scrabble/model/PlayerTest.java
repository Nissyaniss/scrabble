package fr.gameiuter.scrabble.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {
    @Test
    void rackIsEmptyOnNewPlayer() {
        Player player = new Player("Test player");

        assertEquals(0, player.rack().numberOfTiles());
    }

    @Test
    void scoreIsZeroOnNewPlayer() {
        Player player = new Player("Test player");

        assertEquals(0, player.score());
    }

    @Test
    void incrementScore() {
        Player player = new Player("Test player");

        player.incrementScore(5);

        assertEquals(5, player.score());
    }
}
