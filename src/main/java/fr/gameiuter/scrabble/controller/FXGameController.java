package fr.gameiuter.scrabble.controller;

import fr.gameiuter.scrabble.gui.SquareFX;
import fr.gameiuter.scrabble.gui.TileFX;
import fr.gameiuter.scrabble.model.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FXGameController {
    private final GameController gameController;
    private final List<TileFX> placedTilesFX;
    private FXControllerMode mode;

    @FXML
    private Button confirm;
    @FXML
    private Button toggleModeButton;
    @FXML
    private Label labelPlayer1;
    @FXML
    private HBox rack;
    @FXML
    private GridPane board;

    public FXGameController(String player1) {
        this.gameController = new GameController(new Player(player1));
        this.placedTilesFX = new ArrayList<>();
    }

    @FXML
    protected void initialize() {
        this.labelPlayer1.setText("Joueur 1 : " + this.gameController.player().name());
        this.generateGridBase();
        this.gameController.start();
        this.generateRack();

        this.setMode(FXControllerMode.PlaceWord);
    }

    @FXML
    protected void handleConfirm() {
        if (this.mode.equals(FXControllerMode.PlaceWord)) {
            if (placedTilesFX.size() == 0) {
                System.out.println("test");
                return;
            } else {
                Map<Position, Tile> placedTiles = new HashMap<>();
                for (TileFX tileFX : placedTilesFX) {
                    placedTiles.put(tileFX.position(), tileFX.tile());
                }
                System.out.println(gameController.computeScore(placedTiles, Direction.HORIZONTAL));
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

            this.gameController.swap(this.gameController.player(), tilesToSwap);
            this.generateRack();
            this.setMode(FXControllerMode.PlaceWord);
        }
    }

    @FXML
    protected void toggleMode() {
        if (this.mode.equals(FXControllerMode.PlaceWord)) {
            this.setMode(FXControllerMode.SwapLetters);
            this.resetPlacedTiles();
            for (Node node : this.rack.getChildren()) {
                TileFX tile = (TileFX) node;
                tile.freeze();
                tile.setMarkable(true);
            }
        } else {
            this.setMode(FXControllerMode.PlaceWord);
            for (Node node : this.rack.getChildren()) {
                TileFX tile = (TileFX) node;
                tile.unfreeze();
                tile.unmark();
                tile.setMarkable(false);
            }
        }
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

    private void generateRack() {
        List<Tile> rackTile = this.gameController.player().rack().tiles();
        this.rack.getChildren().clear();
        this.rack.setBackground(new Background(new BackgroundFill(new Color(.37254901960784315, .00784313725490196, .12156862745098039, 1), new CornerRadii(6), null)));
        for (Tile tile : rackTile) {
            TileFX tileFX = new TileFX(tile);
            tileFX.setOnMarkChanged(this::tileMarkUpdated);
            this.rack.getChildren().add(tileFX);
        }
    }

    private void setMode(FXControllerMode mode) {
        if (mode.equals(FXControllerMode.PlaceWord)) {
            this.mode = FXControllerMode.PlaceWord;
            this.toggleModeButton.setText("Changer des lettres");
            this.confirm.setDisable(false);
        } else {
            this.mode = FXControllerMode.SwapLetters;
            this.toggleModeButton.setText("Placer un mot");
            this.confirm.setDisable(true);
        }
    }

    public void addToPlacedTilesFX(TileFX tile) {
        if (!this.placedTilesFX.contains(tile)) {
            this.placedTilesFX.add(tile);
        }
    }

    private void resetPlacedTiles() {
        for (TileFX tile : this.placedTilesFX) {
            this.board.getChildren().remove(tile);
            this.rack.getChildren().add(tile);
        }
        this.placedTilesFX.clear();
    }

    private void tileMarkUpdated(Boolean checked) {
        for (Node node : this.rack.getChildren()) {
            TileFX tile = (TileFX) node;
            if (tile.marked()) {
                this.confirm.setDisable(false);
                return;
            }
        }
        this.confirm.setDisable(true);
    }
}
