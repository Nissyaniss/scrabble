package fr.gameiuter.scrabble.controller;

import fr.gameiuter.scrabble.gui.SquareFX;
import fr.gameiuter.scrabble.gui.TileFX;
import fr.gameiuter.scrabble.model.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.*;

public class FXGameController {
    private final GameController gameController;
    private final HashMap<Position, TileFX> placedTilesFX;
    private final ImageView refreshImage = new ImageView(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/img/refresh.png"))));
    private final ImageView cancelImage = new ImageView(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/img/cancel.png"))));
    private final Color primaryColor = new Color(.37254901960784315, .00784313725490196, .12156862745098039, 1);
    private final Color accentColor = new Color(.4980392156862745, .16470588235294117, .27058823529411763, 1);
    private final CornerRadii defaultCornerRadii = new CornerRadii(6);
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
        this.placedTilesFX = new HashMap<>();
    }

    @FXML
    protected void initialize() {
        this.labelPlayer1.setText("Joueur 1 : " + this.gameController.player().name());
        refreshImage.setFitHeight(30);
        refreshImage.setPreserveRatio(true);
        cancelImage.setFitHeight(30);
        cancelImage.setPreserveRatio(true);
        this.generateGridBase();
        this.gameController.start();
        this.generateRack();
        this.setMode(FXControllerMode.PlaceWord);
    }

    @FXML
    protected void handleConfirm() {
        if (this.mode.equals(FXControllerMode.PlaceWord)) {
            if (checkPlacement()) {
                for (TileFX tileFX : placedTilesFX.values()) {
                    tileFX.freeze();
                }
            } else {
                System.out.println(checkPlacement());
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

    private boolean checkPlacement() {
        boolean isFirstTile = true;
        Direction previousDirection = null;
        Direction currentDirection;
        if (this.placedTilesFX.get(new Position(Board.MIDDLE, Board.MIDDLE)) == null) {
            return false;
        } else {
            for (TileFX tile : this.placedTilesFX.values()) {
                if (!tile.isFrozen()) {
                    currentDirection = getDirection(tile.position());
                    if (isFirstTile) {
                        previousDirection = currentDirection;
                        isFirstTile = false;
                    }
                    if (previousDirection == currentDirection && currentDirection != null) {
                        System.out.println("bien" + tile.position());
                        previousDirection = currentDirection;
                    } else {
                        System.out.println("pas bien" + tile.position());
                        return false;
                    }
                    if (!tileHasNeighbors(tile.position())) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    private Direction getDirection(Position position) {
        boolean isHorizontal = true;
        for (TileFX tile : this.placedTilesFX.values()) {
            if (!tile.isFrozen()) {
                if (!tile.position().line().equals(position.line())) {
                    isHorizontal = false;
                    if (!tile.position().column().equals(position.column()))
                        return null;
                }
            }
        }

        if (isHorizontal)
            return Direction.HORIZONTAL;
        else
            return Direction.VERTICAL;
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

    private void generateRack() {
        List<Tile> rackTile = this.gameController.player().rack().tiles();
        this.rack.getChildren().clear();
        this.rack.setBackground(new Background(new BackgroundFill(primaryColor, defaultCornerRadii, null)));

        this.toggleModeButton.setBackground(new Background(new BackgroundFill(accentColor, new CornerRadii(20), null)));
        this.toggleModeButton.setGraphic(refreshImage);
        this.toggleModeButton.setTranslateX(-10);

        for (Tile tile : rackTile) {
            TileFX tileFX = new TileFX(tile);
            tileFX.setOnMarkChanged(this::tileMarkUpdated);
            this.rack.getChildren().add(tileFX);
        }
    }

    private void setMode(FXControllerMode mode) {
        if (mode.equals(FXControllerMode.PlaceWord)) {
            this.mode = FXControllerMode.PlaceWord;
            this.toggleModeButton.setGraphic(refreshImage);
            this.confirm.setDisable(false);
        } else {
            this.mode = FXControllerMode.SwapLetters;
            this.toggleModeButton.setGraphic(cancelImage);
            this.confirm.setDisable(true);
        }
    }

    public void addToPlacedTilesFX(TileFX tile) {
        this.placedTilesFX.put(tile.position(), tile);
    }

    public void removePlacedTilesFX(Position position) {
        this.placedTilesFX.remove(position);
    }

    private void resetPlacedTiles() {
        for (Map.Entry<Position, TileFX> entry : this.placedTilesFX.entrySet()) {
            TileFX tile = entry.getValue();
            if (!tile.isFrozen()) { // frozen tiles are the ones that were already played
                this.board.getChildren().remove(tile);
                this.rack.getChildren().add(tile);
                this.placedTilesFX.remove(entry.getKey());
            }
        }
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
