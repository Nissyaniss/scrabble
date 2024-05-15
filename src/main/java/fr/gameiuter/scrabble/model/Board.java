package fr.gameiuter.scrabble.model;

public class Board {
    public static final Integer SIZE = 15;
    public static final Integer MIDDLE = 7;

    private final Square[][] squares;
    private final Tile[][] placedTiles;

    public Board() {
        this.squares = new Square[SIZE][SIZE];
        this.placedTiles = new Tile[SIZE][SIZE];
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                this.squares[y][x] = Square.NORMAL;
            }
        }
        this.squares[MIDDLE][MIDDLE] = Square.START;
        this.setSquareSymetrical(0, 0, Square.TRIPLE_WORD);
        this.setSquareSymetrical(MIDDLE, 0, Square.TRIPLE_WORD);
        this.setSquareSymetrical(3, 0, Square.DOUBLE_LETTER);
        this.setSquareSymetrical(1, 1, Square.DOUBLE_WORD);
        this.setSquareSymetrical(2, 2, Square.DOUBLE_WORD);
        this.setSquareSymetrical(3, 3, Square.DOUBLE_WORD);
        this.setSquareSymetrical(4, 4, Square.DOUBLE_WORD);
        this.setSquareSymetrical(5, 5, Square.TRIPLE_LETTER);
        this.setSquareSymetrical(6, 6, Square.DOUBLE_LETTER);
        this.setSquareSymetrical(5, 1, Square.TRIPLE_LETTER);
        this.setSquareSymetrical(6, 2, Square.DOUBLE_LETTER);
        this.setSquareSymetrical(7, 3, Square.DOUBLE_LETTER);
    }

    private void setSquareSymetrical(int x, int y, Square square) {
        this.squares[y][x] = square;
        this.squares[SIZE - 1 - y][x] = square;
        this.squares[y][SIZE - 1 - x] = square;
        this.squares[SIZE - 1 - y][SIZE - 1 - x] = square;

        this.squares[x][y] = square;
        this.squares[x][SIZE - 1 - y] = square;
        this.squares[SIZE - 1 - x][y] = square;
        this.squares[SIZE - 1 - x][SIZE - 1 - y] = square;
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
