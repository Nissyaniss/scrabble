package fr.gameiuter.scrabble.model;

import java.util.Map;
import java.util.Optional;

public class Board {
    public static final Integer SIZE = 15;
    public static final Integer MIDDLE = SIZE / 2;

    private final Square[][] squares;
    private final Tile[][] tiles;

    public Board() {
        this.squares = new Square[SIZE][SIZE];
        this.tiles = new Tile[SIZE][SIZE];
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                this.squares[y][x] = Square.NORMAL;
            }
        }
        this.squares[MIDDLE][MIDDLE] = Square.START;
        this.setSquareWithDiagonalSymmetry(0, 0, Square.TRIPLE_WORD);
        this.setSquareWithDiagonalSymmetry(MIDDLE, 0, Square.TRIPLE_WORD);
        this.setSquareWithDiagonalSymmetry(3, 0, Square.DOUBLE_LETTER);
        this.setSquareWithDiagonalSymmetry(1, 1, Square.DOUBLE_WORD);
        this.setSquareWithDiagonalSymmetry(2, 2, Square.DOUBLE_WORD);
        this.setSquareWithDiagonalSymmetry(3, 3, Square.DOUBLE_WORD);
        this.setSquareWithDiagonalSymmetry(4, 4, Square.DOUBLE_WORD);
        this.setSquareWithDiagonalSymmetry(5, 5, Square.TRIPLE_LETTER);
        this.setSquareWithDiagonalSymmetry(6, 6, Square.DOUBLE_LETTER);
        this.setSquareWithDiagonalSymmetry(5, 1, Square.TRIPLE_LETTER);
        this.setSquareWithDiagonalSymmetry(6, 2, Square.DOUBLE_LETTER);
        this.setSquareWithDiagonalSymmetry(7, 3, Square.DOUBLE_LETTER);
    }

    private void setSquareWithDiagonalSymmetry(int x, int y, Square square) {
        this.setSquareWithHorizontalSymmetry(x, y, square);
        this.setSquareWithHorizontalSymmetry(y, x, square);
    }

    private void setSquareWithHorizontalSymmetry(int x, int y, Square square) {
        this.setSquareWithVerticalSymmetry(x, y, square);
        this.setSquareWithVerticalSymmetry(SIZE - 1 - x, y, square);
    }

    private void setSquareWithVerticalSymmetry(int x, int y, Square square) {
        this.squares[y][x] = square;
        this.squares[SIZE - 1 - y][x] = square;
    }

    public boolean hasTileAt(int x, int y) {
        return this.tiles[y][x] != null;
    }

    public void placeTile(Tile tile, int x, int y) {
        tiles[y][x] = tile;
    }

    public Tile[][] tiles() {
        return tiles;
    }

    public Square[][] squares() {
        return squares;
    }

    public boolean checkPlacement(Integer wordLength, Integer x, Integer y, Direction direction) {

        if (tiles[Board.MIDDLE][Board.MIDDLE] == null) {
            return checkFirstMove(wordLength, x, y, direction);
        }

        return checkNormalMoves(wordLength, x, y, direction);
    }

    private boolean checkNormalMoves(Integer wordLength, Integer x, Integer y, Direction direction) {
        if (direction.equals(Direction.HORIZONTAL)) {
            for (int i = x; i <= x + wordLength; i++) {
                if (((x + wordLength >= Board.SIZE && y + 1 <= Board.SIZE && tiles[y + 1][i] != null) // On top & On Bottom
                        || (x + wordLength <= Board.SIZE && y - 1 >= 0 && tiles[y - 1][i] != null)) || (tiles[y][i] != null)) {
                    return true;
                }
            }
            return (x + wordLength + 1 <= Board.SIZE && tiles[y][x + wordLength + 1] != null) // Sides
                    || (x - 1 >= 0 && tiles[y][x - 1] != null);
        } else {
            for (int i = y; i <= y + wordLength; i++) {
                if (((y + wordLength <= Board.SIZE && x + 1 <= Board.SIZE && tiles[i][x + 1] != null) // Sides
                        || (y + wordLength <= Board.SIZE && x - 1 >= 0 && tiles[i][x - 1] != null) || (tiles[i][y] != null))) {
                    return true;
                }
            }
            return (y + wordLength + 1 <= Board.SIZE && tiles[y + wordLength + 1][x] != null) // On top & On Bottom
                    || (y - 1 >= 0 && tiles[y - 1][x] != null);
        }
    }

    private boolean checkFirstMove(Integer wordLength, Integer x, Integer y, Direction direction) {
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
        return false;
    }

    public Integer computeScore(Map<Position, Tile> placedTiles, Direction direction) {
        int score = 0;

        Direction perpendicular = direction == Direction.HORIZONTAL ? Direction.VERTICAL : Direction.HORIZONTAL;

        // the placed tiles all are on the same the line, and are all connected (possibly by tiles that are already on the board)
        // its means we can use any tile of the word and computeWordScore will find the first one
        Position tilePosition = placedTiles.keySet().iterator().next();
        score += this.computeWordScore(placedTiles, tilePosition.x(), tilePosition.y(), direction);

        for (Position position : placedTiles.keySet())
            score += this.computeWordScore(placedTiles, position.x(), position.y(), perpendicular);

        return score;
    }

    private Integer computeWordScore(Map<Position, Tile> placedTiles, int x, int y, Direction direction) {
        int tilesInWord = 0;
        int wordMultiplier = 1;
        int score = 0;

        if (direction == Direction.HORIZONTAL) {
            int startX = x;
            while (getAnyTile(placedTiles, startX - 1, y).isPresent()) startX--;
            x = startX;
        } else {
            int startY = y;
            while (getAnyTile(placedTiles, x, startY - 1).isPresent()) startY--;
            y = startY;
        }

        while (getAnyTile(placedTiles, x, y).isPresent()) {
            Tile tile;
            int tileMultiplier = 1;
            if (this.tiles[y][x] != null) {
                tile = this.tiles[y][x];
            } else {
                tile = placedTiles.get(new Position(x, y));
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

    private Optional<Tile> getAnyTile(Map<Position, Tile> additionalTiles, int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE)
            return Optional.empty();

        if (this.tiles[y][x] != null)
            return Optional.of(this.tiles[y][x]);
        else
            return Optional.ofNullable(additionalTiles.get(new Position(x, y)));
    }
}
