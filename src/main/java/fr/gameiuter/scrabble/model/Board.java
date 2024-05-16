package fr.gameiuter.scrabble.model;

import java.util.Map;

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
        this.setSquareSymmetrical(0, 0, Square.TRIPLE_WORD);
        this.setSquareSymmetrical(MIDDLE, 0, Square.TRIPLE_WORD);
        this.setSquareSymmetrical(3, 0, Square.DOUBLE_LETTER);
        this.setSquareSymmetrical(1, 1, Square.DOUBLE_WORD);
        this.setSquareSymmetrical(2, 2, Square.DOUBLE_WORD);
        this.setSquareSymmetrical(3, 3, Square.DOUBLE_WORD);
        this.setSquareSymmetrical(4, 4, Square.DOUBLE_WORD);
        this.setSquareSymmetrical(5, 5, Square.TRIPLE_LETTER);
        this.setSquareSymmetrical(6, 6, Square.DOUBLE_LETTER);
        this.setSquareSymmetrical(5, 1, Square.TRIPLE_LETTER);
        this.setSquareSymmetrical(6, 2, Square.DOUBLE_LETTER);
        this.setSquareSymmetrical(7, 3, Square.DOUBLE_LETTER);
    }

    private void setSquareSymmetrical(int x, int y, Square square) {
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

    public Boolean checkPlacement(Integer wordLength, Integer x, Integer y, Direction direction) {
        // First Move

        if (placedTiles[Board.MIDDLE][Board.MIDDLE] == null) {
            if (direction.equals(Direction.HORIZONTAL)) {
                for (int i = x; i <= x + wordLength; i++) {
                    if (squares[y][i].equals(Square.START)) {
                        return true;
                    }
                }
            } else {
                for (int i = y; i <= y + wordLength; i++) {
                    if (squares[i][x].equals(Square.START)) {
                        return true;
                    }
                }
            }
        }

        //Normal Moves

        boolean isAllowed = false;

        if (direction.equals(Direction.HORIZONTAL)) {
            for (int i = x; i <= x + wordLength; i++) {
                if ((x + wordLength >= Board.SIZE && y + 1 <= Board.SIZE && placedTiles[y + 1][i] != null) // On top & On Bottom
                        || (x + wordLength <= 0 && y - 1 >= 0 && placedTiles[y - 1][i] != null)) {
                    isAllowed = true;
                    break;
                }
            }
            if ((x + wordLength + 1 <= Board.SIZE && placedTiles[y][x + wordLength + 1] != null) // Sides
                    || (x - 1 >= 0 && placedTiles[y][x - 1] != null)) {
                isAllowed = true;
            }
        } else {
            for (int i = y; i <= y + wordLength; i++) {
                if ((y + wordLength <= Board.SIZE && x + 1 <= Board.SIZE && placedTiles[i][x + 1] != null)
                        || (y + wordLength <= Board.SIZE && x - 1 >= 0 && placedTiles[i][x - 1] != null)) {
                    isAllowed = true;
                    break;
                }
            }
            if ((y + wordLength + 1 <= Board.SIZE && placedTiles[y + wordLength + 1][x] != null)
                    || (y - 1 >= 0 && placedTiles[y - 1][x] != null)) {
                isAllowed = true;
            }
        }

        return isAllowed;
    }

    public Integer computeScore(Map<Coords, Tile> placedTiles, Direction direction) {
        int score = 0;

        Direction perpendicular = direction == Direction.HORIZONTAL ? Direction.VERTICAL : Direction.HORIZONTAL;

        // the placed tiles all are on the same the line, and are all connected (possibly by tiles that are already on the board)
        // its means we can use any tile of the word and computeWordScore will find the first one
        Coords tileCoords = placedTiles.keySet().iterator().next();
        score += this.computeWordScore(placedTiles, tileCoords.getX(), tileCoords.getY(), direction);

        for (Coords coords : placedTiles.keySet())
            score += this.computeWordScore(placedTiles, coords.getX(), coords.getY(), perpendicular);

        return score;
    }

    private Integer computeWordScore(Map<Coords, Tile> placedTiles, int x, int y, Direction direction) {
        int tilesInWord = 0;
        int wordMultiplier = 1;
        int score = 0;

        if (direction == Direction.HORIZONTAL) {
            int startX = x;
            while (getAnyTile(placedTiles, startX - 1, y) != null) startX--;
            x = startX;
        } else {
            int startY = y;
            while (getAnyTile(placedTiles, x, startY - 1) != null) startY--;
            y = startY;
        }

        while (getAnyTile(placedTiles, x, y) != null) {
            Tile tile;
            int tileMultiplier = 1;
            if (this.placedTiles[y][x] != null) {
                tile = this.placedTiles[y][x];
            } else {
                tile = placedTiles.get(new Coords(x, y));
                tileMultiplier = this.squares[y][x].letterMultiplier();
                wordMultiplier *= this.squares[y][x].wordMultiplier();
            }
            score += tile.score() * tileMultiplier;
            if (direction == Direction.HORIZONTAL)
                x++;
            else
                y++;
            tilesInWord++;
        }

        if (tilesInWord > 1)
            return score * wordMultiplier;
        else
            return 0;
    }

    private Tile getAnyTile(Map<Coords, Tile> additionalTiles, int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE)
            return null;

        if (this.placedTiles[y][x] != null)
            return this.placedTiles[y][x];
        else
            return additionalTiles.get(new Coords(x, y));
    }
}
