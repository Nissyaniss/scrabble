package fr.gameiuter.scrabble.gui;

import fr.gameiuter.scrabble.controller.FXControllerMode;
import fr.gameiuter.scrabble.controller.FXGameController;
import fr.gameiuter.scrabble.controller.GameController;
import fr.gameiuter.scrabble.model.Tile;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Objects;

public class RackFX {
    private final Color primaryColor = Color.LIGHTGRAY;
    private final Color accentColor = Color.GRAY;
    private final CornerRadii defaultCornerRadii = new CornerRadii(6);
    private final ImageView exchangeImage = new ImageView(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/img/exchange.png"))));
    private final ImageView cancelImage = new ImageView(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/img/cancel.png"))));
    private final ImageView shuffleImage = new ImageView(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/img/shuffle.png"))));

    private final HBox hBox;
    private final Button toggleModeButton;
    private final GameController gameController;
    private final Button confirm;
    private final Button randomButton;
    private FXControllerMode mode;

    public RackFX(HBox hBox, GameController gameController, Button toggleModeButton, Button confirm, FXGameController fxGameController, Button shuffleButton) {
        this.hBox = hBox;
        this.confirm = confirm;
        this.toggleModeButton = toggleModeButton;
        this.gameController = gameController;
        this.randomButton = shuffleButton;
        this.mode = FXControllerMode.PlaceWord;
        this.refreshRack();
        this.manageTargetDragAndDrop(fxGameController);
    }

    public void refreshRack() {
        exchangeImage.setFitHeight(30);
        exchangeImage.setPreserveRatio(true);
        cancelImage.setFitHeight(30);
        cancelImage.setPreserveRatio(true);
        shuffleImage.setFitHeight(30);
        shuffleImage.setPreserveRatio(true);
        List<Tile> rackTile = this.gameController.player(1).rack().tiles();
        this.hBox.getChildren().clear();
        this.hBox.setBackground(new Background(new BackgroundFill(primaryColor, defaultCornerRadii, null)));

        this.toggleModeButton.setBackground(new Background(new BackgroundFill(accentColor, new CornerRadii(20), null)));
        this.toggleModeButton.setGraphic(exchangeImage);
        this.toggleModeButton.setTranslateX(-10);

        this.randomButton.setBackground(new Background(new BackgroundFill(accentColor, new CornerRadii(20), null)));
        this.randomButton.setGraphic(shuffleImage);
        this.randomButton.setTranslateX(-10);

        for (Tile tile : rackTile) {
            TileFX tileFX = new TileFX(tile);
            tileFX.setOnMarkChanged(this::tileMarkUpdated);
            this.hBox.getChildren().add(tileFX);
        }
    }

    private void tileMarkUpdated(Boolean checked) {
        for (Node node : this.hBox.getChildren()) {
            TileFX tile = (TileFX) node;
            if (tile.marked()) {
                this.confirm.setDisable(false);
                return;
            }
        }
        this.confirm.setDisable(true);
    }

    public FXControllerMode getMode() {
        return this.mode;
    }

    public void setMode(FXControllerMode mode) {
        if (mode.equals(FXControllerMode.PlaceWord)) {
            this.mode = FXControllerMode.PlaceWord;
            this.toggleModeButton.setGraphic(exchangeImage);
            this.confirm.setDisable(false);
        } else {
            this.mode = FXControllerMode.SwapLetters;
            this.toggleModeButton.setGraphic(cancelImage);
            this.confirm.setDisable(true);
        }
    }

    public void manageTargetDragAndDrop(FXGameController gameController) {
        this.hBox.setOnDragOver(event -> {
            if (event.getGestureSource() != this && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
        });
        this.hBox.setOnDragDropped(event -> {
            TileFX tile = (TileFX) event.getGestureSource();
            if (tile.position() != null) {
                GridPane boardFX = (GridPane) tile.getParent();
                boardFX.getChildren().remove(tile);
                this.hBox.getChildren().add(tile);
                gameController.removePlacedTilesFX(tile.position(), true);
                tile.setPosition(null);
                event.setDropCompleted(true);
            }
        });
    }
}
