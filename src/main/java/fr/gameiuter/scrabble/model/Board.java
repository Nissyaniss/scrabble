package fr.gameiuter.scrabble.model;

import java.util.List;

public class Board {
    private static final Integer SIZE = 15;
    private static final Integer MIDDLE = 7;

    private final Square[][] squares;
    private List<Tile> placedTiles;

    public Board() {
        this.squares = new Square[SIZE][SIZE];
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                this.squares[y][x] = Square.NORMAL;
            }
        }
        this.squares[MIDDLE][MIDDLE] = Square.START;
    }

    public void placeTile(Tile tile) {
        // TODO
    }

    public String display() {
        StringBuilder builder = new StringBuilder();
        builder.append("┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐\n");
        for (int i = 0; i < SIZE - 1; i++) {
            for (Square square : this.squares[i]) {
                builder.append("│ ");
                builder.append(square.symbol());
                builder.append(" ");
            }
            builder.append("│\n");
            builder.append("├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤\n");
        }

        for (Square square : this.squares[SIZE - 1]) {
            builder.append("│ ");
            builder.append(square.symbol());
            builder.append(" ");
        }
        builder.append("│\n");
        builder.append("└───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘");

        return builder.toString();
    }
}
