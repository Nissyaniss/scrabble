package fr.gameiuter.scrabble.model;

import java.util.List;

public class Board {
    private final Square[][] squares;
    private List<Tile> placedTiles;

    public Board() {
        this.squares = new Square[15][15];
        for (int y = 0; y < 15; y++) {
            for (int x = 0; x < 15; x++) {
                this.squares[y][x] = Square.NORMAL;
            }
        }
        this.squares[7][7] = Square.START;
    }

    public void placeTile(Tile tile) {
        // TODO
    }

    public String display() {
        StringBuilder builder = new StringBuilder();
        builder.append("┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐\n");
        for (int i = 0; i < 13; i++) {
            for (Square square : this.squares[i]) {
                builder.append("│ ");
                builder.append(square.symbol());
                builder.append(" ");
            }
            builder.append("│\n");
            builder.append("├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤\n");
        }

        for (Square square : this.squares[14]) {
            builder.append("│ ");
            builder.append(square.symbol());
            builder.append(" ");
        }
        builder.append("│\n");
        builder.append("└───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘");

        return builder.toString();
    }
}
