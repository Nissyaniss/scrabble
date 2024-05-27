package fr.gameiuter.scrabble.model;

import java.util.ArrayList;
import java.util.List;
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

    public Square getSquareAt(Position position) {
        return this.squares[position.line()][position.column()];
    }

    public Tile getTileAt(Position position) {
        return this.tiles[position.line()][position.column()];
    }

    public boolean hasTileAt(Position position) {
        if (!position.containedWithinBounds(0, LAST_LINE_OR_COLUMN))
            return false;
        return this.getTileAt(position) != Tile.NO;
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

    public boolean checkPlacement(Integer wordLength, Position position, Direction direction) {
        if (!this.hasTileAt(new Position(Board.MIDDLE, Board.MIDDLE))) {
            return checkFirstMove(wordLength, position, direction);
        }

        return checkNormalMove(wordLength, position, direction);
    }

    private boolean checkNormalMove(Integer wordLength, Position position, Direction direction) {
        for (int i = 0; i <= wordLength; i++) {
            if (this.tileHasNeighbors(position)) {
                return true;
            }
            position = position.next(direction);
        }

        return false;
    }

    private boolean tileHasNeighbors(Position position) {
        return (this.hasTileAt(position.next(Direction.HORIZONTAL)) || (this.hasTileAt(position.previous(Direction.HORIZONTAL)))
                || (this.hasTileAt(position.next(Direction.VERTICAL))) || (this.hasTileAt(position.previous(Direction.VERTICAL))));
    }

    private boolean checkFirstMove(Integer wordLength, Position position, Direction direction) {
        return ((direction.equals(Direction.HORIZONTAL) && position.line().equals(Board.MIDDLE) && position.column() <= Board.MIDDLE && position.column() + wordLength >= Board.MIDDLE)
                || (direction.equals(Direction.VERTICAL) && position.column().equals(Board.MIDDLE) && position.line() <= Board.MIDDLE && position.line() + wordLength >= Board.MIDDLE));
    }

    public Word getWordAt(Position position, Direction direction, Map<Position, Tile> additionalTiles) {
        // go back to start of the word if we're not at it yet
        while (this.getTileAtWithAdditional(position.previous(direction), additionalTiles).isPresent())
            position = position.previous(direction);

        Position startPosition = position;

        List<Tile> tiles = new ArrayList<>();
        while (this.getTileAtWithAdditional(position, additionalTiles).isPresent()) {
            tiles.add(this.getTileAtWithAdditional(position, additionalTiles).get());
            position = position.next(direction);
        }

        return new Word(startPosition, direction, tiles);
    }

    public Optional<Tile> getTileAtWithAdditional(Position position, Map<Position, Tile> additionalTiles) {
        if (this.hasTileAt(position))
            return Optional.of(this.getTileAt(position));
        else
            return Optional.ofNullable(additionalTiles.get(position));
    }
}
