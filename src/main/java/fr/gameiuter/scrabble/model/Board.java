package fr.gameiuter.scrabble.model;

public class Board {
    public static final Integer SIZE = 15;
    public static final Integer MIDDLE = 7;

    private final Square[][] squares;
    private Tile[][] placedTiles;

    public Board() {
        this.squares = new Square[SIZE][SIZE];
        this.placedTiles = new Tile[SIZE][SIZE];
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                this.squares[y][x] = Square.NORMAL;
            }
        }
        this.squares[MIDDLE][MIDDLE] = Square.START;
    }

    public void placeTile(Tile tile, int x, int y) {
        placedTiles[y][x] = tile;
    }

    public String display() {
        StringBuilder builder = new StringBuilder();
        builder.append("┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐\n");
        for (int y = 0; y < SIZE - 1; y++) {
            for (int x = 0; x < SIZE; x++) {
                builder.append("│ ");
                if (this.placedTiles[y][x] != null) {
                    builder.append(this.placedTiles[y][x].letter());
                } else {
                    builder.append(this.squares[y][x].symbol());
                }
                builder.append(" ");
            }
            builder.append("│\n");
            builder.append("├───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┼───┤\n");
        }

        for (int x = 0; x < SIZE; x++) {
            builder.append("│ ");
            if (this.placedTiles[SIZE - 1][x] != null) {
                builder.append(this.placedTiles[SIZE - 1][x].letter());
            } else {
                builder.append(this.squares[SIZE - 1][x].symbol());
            }
            builder.append(" ");
        }
        builder.append("│\n");
        builder.append("└───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘");

        return builder.toString();
    }
}
