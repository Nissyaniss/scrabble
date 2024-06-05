package fr.gameiuter.scrabble.controller;

import fr.gameiuter.scrabble.gui.RackFX;
import fr.gameiuter.scrabble.gui.SquareFX;
import fr.gameiuter.scrabble.gui.TileFX;
import fr.gameiuter.scrabble.model.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.*;
import java.util.stream.Collectors;

public class FXGameController {
    private final GameController gameController;
    private final HashMap<Position, TileFX> placedTilesFX;
    private RackFX rackFX;

    @FXML
    private Button confirm;
    @FXML
    private Button toggleModeButton;
    @FXML
    private Label labelPlayer1;
    @FXML
    private Label player1Score;
    @FXML
    private HBox rack;
    @FXML
    private GridPane board;
    @FXML
    private Button randomButton;

    public FXGameController(String player1, String player2) {
        this.gameController = new GameController(new Player(player1), new Player(player2));
        this.placedTilesFX = new HashMap<>();
    }

    @FXML
    protected void initialize() {
        this.labelPlayer1.setText("Joueur 1 : " + this.gameController.player(1).name());
        this.generateGridBase();
        this.gameController.start();
        this.updateScores();
        this.rackFX = new RackFX(rack, gameController, toggleModeButton, confirm, this, randomButton);
    }

    @FXML
    protected void handleConfirm() {
        if (this.rackFX.getMode().equals(FXControllerMode.PlaceWord)) {
            if (this.checkPlacement() && this.isExistingWord()) {
                Player player = this.gameController.player(1);
                HashMap<Position, Tile> newTiles = new HashMap<>();

                for (TileFX tileFX : placedTilesFX.values()) {
                    if (!tileFX.isFrozen()) {
                        newTiles.put(tileFX.position(), tileFX.tile());
                        tileFX.freeze();
                        player.rack().remove(tileFX.tile());
                        this.gameController.board().placeTile(tileFX.tile(), tileFX.position().column(), tileFX.position().line());
                    }
                }

                int score = this.gameController.computeScore(newTiles, this.getDirection(newTiles.keySet().iterator().next()));
                player.incrementScore(score);
                this.gameController.draw(player);
                this.rackFX.refreshRack();
                this.updateScores();
            }
        } else {
            List<Tile> tilesToSwap = new ArrayList<>();
            for (Node node : this.rack.getChildren()) {
                TileFX tileFX = (TileFX) node;
                if (tileFX.marked()) {
                    tilesToSwap.add(tileFX.tile());
                }
            }
            this.rack.getChildren().clear();

            this.gameController.swap(this.gameController.player(1), tilesToSwap);
            this.rackFX.refreshRack();
            this.rackFX.setMode(FXControllerMode.PlaceWord);
        }
    }

    @FXML
    protected void toggleMode() {
        if (this.rackFX.getMode().equals(FXControllerMode.PlaceWord)) {
            this.rackFX.setMode(FXControllerMode.SwapLetters);
            this.resetPlacedTiles();
            for (Node node : this.rack.getChildren()) {
                TileFX tile = (TileFX) node;
                tile.freeze();
                tile.setMarkable(true);
            }
        } else {
            this.rackFX.setMode(FXControllerMode.PlaceWord);
            for (Node node : this.rack.getChildren()) {
                TileFX tile = (TileFX) node;
                tile.unfreeze();
                tile.unmark();
                tile.setMarkable(false);
            }
        }
    }

    @FXML
    protected void randomizeRack() {
        Collections.shuffle(this.gameController.player(1).rack().tiles());
        this.rackFX.refreshRack();
    }

    private void generateGridBase() {
        for (int line = 0; line < Board.SIZE; line++) {
            for (int column = 0; column < Board.SIZE; column++) {
                Square square = this.gameController.board().getSquareAt(new Position(column, line));
                SquareFX squareFX = new SquareFX(square, new Position(column, line), this);
                this.board.add(squareFX, column, line);
            }
        }
    }

    private boolean isExistingWord() {
        Map<Position, Tile> placedTiles = this.placedTilesFX
                .entrySet()
                .stream()
                .filter(entry -> !entry.getValue().isFrozen())
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().tile()));

        Map.Entry<Position, Tile> entry = placedTiles.entrySet().iterator().next();
        Position pos = entry.getKey();
        Word word = this.gameController.board().getWordAt(pos, this.getDirection(pos), placedTiles);
        return this.gameController.isExistingWord(word);
    }

    private boolean checkPlacement() {
        boolean isFirstTile = true;
        Direction previousDirection = null;
        Direction currentDirection;
        if (this.placedTilesFX.get(new Position(Board.MIDDLE, Board.MIDDLE)) == null) {
            return false;
        } else {
            for (TileFX tile : this.placedTilesFX.values()) {
                if (!tile.isFrozen()) {
                    currentDirection = this.getDirection(tile.position());
                    if (isFirstTile) {
                        previousDirection = currentDirection;
                        isFirstTile = false;
                    }
                    if (previousDirection == currentDirection && currentDirection != null) {
                        previousDirection = currentDirection;
                    } else {
                        return false;
                    }
                    if (!this.tileHasNeighbors(tile.position())) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    private Direction getDirection(Position position) {
        boolean isVertical = false;
        for (TileFX tile : this.placedTilesFX.values()) {
            if (!tile.isFrozen()) {
                if (!tile.position().line().equals(position.line())) {
                    isVertical = true;
                    if (!tile.position().column().equals(position.column()))
                        return null;
                }
            }
        }

        if (isVertical)
            return Direction.VERTICAL;
        else
            return Direction.HORIZONTAL;
    }

    private boolean tileHasNeighbors(Position position) {
        return (this.hasTileAt(position.next(Direction.HORIZONTAL)) || (this.hasTileAt(position.previous(Direction.HORIZONTAL)))
                || (this.hasTileAt(position.next(Direction.VERTICAL))) || (this.hasTileAt(position.previous(Direction.VERTICAL))));
    }

    public boolean hasTileAt(Position position) {
        if (!position.containedWithinBounds(0, Board.LAST_LINE_OR_COLUMN))
            return false;
        return this.getTileAt(position) != TileFX.NO;
    }

    public TileFX getTileAt(Position position) {
        return this.placedTilesFX.get(position);
    }

    public void addToPlacedTilesFX(TileFX tileFX) {
        if (tileFX.tile().isJoker() && tileFX.tile().letter() == '*') {
            char letter;
            while (true) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setHeaderText("Quelle lettre voulez-vous que ce soit ?");
                Optional<String> text = dialog.showAndWait();
                if (text.isPresent() && text.get().length() == 1) {
                    letter = text.get().charAt(0);
                    break;
                }
            }
            tileFX.setLetter(letter);
        }
        this.placedTilesFX.put(tileFX.position(), tileFX);
    }

    public void removePlacedTilesFX(Position position, boolean resetJoker) {
        TileFX tileFX = this.placedTilesFX.remove(position);
        if (resetJoker && tileFX != null && tileFX.tile().isJoker()) {
            tileFX.setLetter('*');
        }
    }

    private void resetPlacedTiles() {
        List<Position> positionsToRemove = new ArrayList<>();
        for (Map.Entry<Position, TileFX> entry : this.placedTilesFX.entrySet()) {
            TileFX tile = entry.getValue();
            if (!tile.isFrozen()) { // frozen tiles are the ones that were already played
                this.board.getChildren().remove(tile);
                this.rack.getChildren().add(tile);
                positionsToRemove.add(entry.getKey());
            }
        }
        for (Position position : positionsToRemove) {
            TileFX tileFX = this.placedTilesFX.remove(position);
            if (tileFX.tile().isJoker()) {
                tileFX.setLetter('*');
            }
        }
    }

    private void updateScores() {
        this.player1Score.setText("Score: " + this.gameController.player(1).score());
    }

    public void gridUpdated() {
        this.confirm.setDisable(!(this.checkPlacement() && this.isExistingWord()));
    }
}
