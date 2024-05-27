package fr.gameiuter.scrabble.model;

import java.util.Map;
import java.util.Optional;

public class Board {
    public static final Integer SIZE = 15;
    public static final Integer MIDDLE = SIZE / 2;
    public static final Integer LAST_LINE_OR_COLUMN = SIZE - 1;

    private final Square[][] squares;
    private final Tile[][] tiles;

    public Board() {
        this.squares = new Square[SIZE][SIZE];
        this.tiles = new Tile[SIZE][SIZE];
        for (int line = 0; line < SIZE; line++) {
            for (int column = 0; column < SIZE; column++) {
                this.squares[line][column] = Square.NORMAL;
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

    private void setSquareWithDiagonalSymmetry(int column, int line, Square square) {
        this.setSquareWithHorizontalSymmetry(column, line, square);
        this.setSquareWithHorizontalSymmetry(line, column, square);
    }

    private void setSquareWithHorizontalSymmetry(int column, int line, Square square) {
        this.setSquareWithVerticalSymmetry(column, line, square);
        this.setSquareWithVerticalSymmetry(LAST_LINE_OR_COLUMN - column, line, square);
    }

    private void setSquareWithVerticalSymmetry(int column, int line, Square square) {
        this.squares[line][column] = square;
        this.squares[LAST_LINE_OR_COLUMN - line][column] = square;
    }

    public boolean hasTileAt(int column, int line) {
        if (column < 0 || column >= SIZE || line < 0 || line >= SIZE)
            return false;
        return this.tiles[line][column] != Tile.NO;
    }

    public void placeTile(Tile tile, int column, int line) {
        tiles[line][column] = tile;
    }

    public Tile[][] tiles() {
        return tiles;
    }

    public Square[][] squares() {
        return squares;
    }

    public boolean checkPlacement(Integer wordLength, Integer x, Integer y, Direction direction) {

        if (this.hasTileAt(Board.MIDDLE, Board.MIDDLE)) {
            return checkFirstMove(wordLength, x, y, direction);
        }

        return checkNormalMove(wordLength, column, line, direction);
    }

    private boolean checkNormalMoves(Integer wordLength, Integer column, Integer line, Direction direction) {
        if (direction.equals(Direction.HORIZONTAL)) {
            for (int letterIndex = column; letterIndex <= column + wordLength; letterIndex++) {
                if (this.tileHasNeighbors(column, letterIndex)) {
                    return true;
                }
            }
        } else {
            for (int letterIndex = line; letterIndex <= line + wordLength; letterIndex++) {
                if (this.tileHasNeighbors(column, letterIndex)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean tileHasNeighbors(Integer column, Integer line) {
        return (this.hasTileAt(column + 1, line)) || (this.hasTileAt(column - 1, line))
                || (this.hasTileAt(column, line + 1)) || (this.hasTileAt(column - 1, line - 1));
    }

    private boolean checkFirstMove(Integer wordLength, Integer column, Integer line, Direction direction) {
        return ((direction.equals(Direction.HORIZONTAL) && (line.equals(Board.MIDDLE) && column <= Board.MIDDLE && column + wordLength >= Board.MIDDLE))
                || (direction.equals(Direction.VERTICAL) && column.equals(Board.MIDDLE) && line <= Board.MIDDLE && line + wordLength >= Board.MIDDLE));
    }

    public Integer computeScore(Map<Position, Tile> placedTiles, Direction direction) {
        int score = 0;

        Direction perpendicular = direction == Direction.HORIZONTAL ? Direction.VERTICAL : Direction.HORIZONTAL;

        // the placed tiles all are on the same the line, and are all connected (possibly by tiles that are already on the board)
        // its means we can use any tile of the word and computeWordScore will find the first one
        Position tilePosition = placedTiles.keySet().iterator().next();
        score += this.computeWordScore(placedTiles, tilePosition.column(), tilePosition.line(), direction);

        for (Position position : placedTiles.keySet())
            score += this.computeWordScore(placedTiles, position.column(), position.line(), perpendicular);

        return score;
    }

    private Integer computeWordScore(Map<Position, Tile> placedTiles, int column, int line, Direction direction) {
        int tilesInWord = 0;
        int wordMultiplier = 1;
        int score = 0;

        if (direction == Direction.HORIZONTAL) {
            int startColumn = column;
            while (getAnyTile(placedTiles, startColumn - 1, line).isPresent()) startColumn--;
            column = startColumn;
        } else {
            int startLine = line;
            while (getAnyTile(placedTiles, column, startLine - 1).isPresent()) startLine--;
            line = startLine;
        }

        while (getAnyTile(placedTiles, column, line).isPresent()) {
            Tile tile;
            int tileMultiplier = 1;
            if (this.tiles[line][column] != null) {
                tile = this.tiles[line][column];
            } else {
                tile = placedTiles.get(new Position(column, line));
                tileMultiplier = this.squares[line][column].letterMultiplier();
                wordMultiplier *= this.squares[line][column].wordMultiplier();
            }
            score += tile.score() * tileMultiplier;
            if (direction == Direction.HORIZONTAL)
                column++;
            else
                line++;
            tilesInWord++;
        }

        if (tilesInWord > 1)
            return score * wordMultiplier;
        else
            return 0;
    }

    private Optional<Tile> getAnyTile(Map<Position, Tile> additionalTiles, int column, int line) {
        if (column < 0 || column >= SIZE || line < 0 || line >= SIZE)
            return Optional.empty();

        if (this.tiles[line][column] != null)
            return Optional.of(this.tiles[line][column]);
        else
            return Optional.ofNullable(additionalTiles.get(new Position(column, line)));
    }
}
