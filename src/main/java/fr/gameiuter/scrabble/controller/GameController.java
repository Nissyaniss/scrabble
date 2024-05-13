package fr.gameiuter.scrabble.controller;

import fr.gameiuter.scrabble.model.*;

import java.util.Collection;

public class GameController {
    private final Pouch pouch;
    private final Player player;

    public GameController(Player player) {
        this.pouch = new Pouch();
        this.player = player;
    }

    public void start() {
        this.draw(this.player);
    }

    public void draw(Player player) {
        while (player.getRack().numberOfTiles() < 7) {
            player.getRack().add(pouch.draw());
        }
    }

    public void swap(Player player, Collection<Tile> tiles) {
        for (Tile tile : tiles) {
            player.getRack().remove(tile);
            this.pouch.putBack(tile);
        }
        this.draw(player);
    }

    public Pouch getPouch() {
        return this.pouch;
    }

    public Player player() {
        return this.player;
    }

    @Override
    public String toString() {
        return "GameController{" +
                "pouch=" + pouch +
                '}';
    }

    public Boolean checkPlacement(String word, Integer x, Integer y, Direction direction, Tile[][] placedTiles, Square[][] squares) {
        boolean isAllowed = false;

        // First Move

        if (placedTiles[Board.MIDDLE][Board.MIDDLE] == null) {
            if (direction.equals(Direction.HORIZONTAL)) {
                for (int i = 0; i < word.length(); i++) {
                    if (squares[Board.MIDDLE][i].equals(Square.START)) {
                        isAllowed = true;
                        break;
                    }
                }
            } else {
                for (int i = 0; i < word.length(); i++) {
                    if (squares[i][Board.MIDDLE].equals(Square.START)) {
                        isAllowed = true;
                        break;
                    }
                }
            }
        }

        //Normal Moves

        if (isAllowed && direction.equals(Direction.HORIZONTAL)) {
            isAllowed = false;
            for (int i = 0; i < word.length(); i++) {
                if ((y + 1 <= Board.SIZE && placedTiles[y + 1][i] != null)
                        || (y - 1 >= 0 && placedTiles[y - 1][i] != null)) {
                    isAllowed = true;
                    break;
                }
            }
            if ((x <= Board.SIZE && placedTiles[y][x + word.length() - 1] != null)
                    || (x > 0 && placedTiles[y][x - 1] != null)) {
                isAllowed = true;
            }
        } else if (isAllowed) {
            isAllowed = false;
            for (int i = 0; i < word.length(); i++) {
                if ((x + 1 <= Board.SIZE && placedTiles[i][x + 1] != null)
                        || (x - 1 >= 0 && placedTiles[i][x - 1] != null)) {
                    isAllowed = true;
                    break;
                }
            }
            if ((x <= Board.SIZE && placedTiles[y][x + word.length() - 1] != null)
                    || (x > 0 && placedTiles[y][x - 1] != null)) {
                isAllowed = true;
            }
        }

        return isAllowed;
    }
}
