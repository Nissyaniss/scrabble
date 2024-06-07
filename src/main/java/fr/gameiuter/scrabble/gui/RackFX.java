package fr.gameiuter.scrabble.gui;

import fr.gameiuter.scrabble.controller.FXControllerMode;
import fr.gameiuter.scrabble.controller.FXGameController;
import fr.gameiuter.scrabble.controller.GameController;
import fr.gameiuter.scrabble.model.Tile;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Objects;

public class RackFX {
    private static final Color primaryColor = Color.LIGHTGRAY;
    private static final Color accentColor = Color.GRAY;
    private static final CornerRadii defaultCornerRadii = new CornerRadii(6);
    private static final ImageView exchangeImage = new ImageView(new Image(Objects.requireNonNull(RackFX.class.getResourceAsStream("/img/exchange.png")), 30, 30, true, true));
    private static final ImageView cancelImage = new ImageView(new Image(Objects.requireNonNull(RackFX.class.getResourceAsStream("/img/cancel.png")), 30, 30, true, true));
    private static final ImageView shuffleImage = new ImageView(new Image(Objects.requireNonNull(RackFX.class.getResourceAsStream("/img/shuffle.png")), 30, 30, true, true));

    private final HBox hBox;
    private final Button toggleModeButton;
    private final GameController gameController;
    private final FXGameController gameControllerFX;
    private final Button confirm;
    private FXControllerMode mode;

    public RackFX(HBox hBox, GameController gameController, Button toggleModeButton, Button confirm, FXGameController fxGameController, Button shuffleButton, GridPane board) {
        shuffleButton.setTooltip(new Tooltip("Randomizer l'ordre des lettres"));
        toggleModeButton.setTooltip(new Tooltip("Échanger des lettres"));

        toggleModeButton.setBackground(new Background(new BackgroundFill(accentColor, new CornerRadii(20), null)));
        toggleModeButton.setTranslateX(-10);

        shuffleButton.setBackground(new Background(new BackgroundFill(accentColor, new CornerRadii(20), null)));
        shuffleButton.setGraphic(shuffleImage);
        shuffleButton.setTranslateX(-10);

        hBox.setBackground(new Background(new BackgroundFill(primaryColor, defaultCornerRadii, null)));

        this.hBox = hBox;
        this.confirm = confirm;
        this.toggleModeButton = toggleModeButton;
        this.gameController = gameController;
        this.gameControllerFX = fxGameController;
        this.setMode(FXControllerMode.PLACE_WORD);
        this.refreshRack();
        this.manageTargetDragAndDrop(fxGameController, board);
    }

    public void refreshRack() {
        List<Tile> rackTile = this.gameController.player().rack().tiles();
        this.hBox.getChildren().clear();

        for (Tile tile : rackTile) {
            TileFX tileFX = new TileFX(tile, this.hBox, this.gameControllerFX);
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
        this.mode = mode;
        this.confirm.setDisable(true);
        if (mode.equals(FXControllerMode.PLACE_WORD)) {
            this.toggleModeButton.setGraphic(exchangeImage);
            this.toggleModeButton.setTooltip(new Tooltip("Échanger des lettres"));
        } else {
            this.toggleModeButton.setGraphic(cancelImage);
            this.toggleModeButton.setTooltip(new Tooltip("Placer des lettres"));
        }
    }

    public void manageTargetDragAndDrop(FXGameController gameController, GridPane board) {
        this.hBox.setOnDragOver(event -> {
            TileFX source = (TileFX) event.getGestureSource();
            if (source.getParent().equals(board)) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
        this.hBox.setOnDragDropped(event -> {
            TileFX tile = (TileFX) event.getGestureSource();
            if (tile.getParent().equals(board) && tile.position() != null) {
                GridPane boardFX = (GridPane) tile.getParent();
                boardFX.getChildren().remove(tile);
                this.hBox.getChildren().add(tile);
                gameController.removePlacedTilesFX(tile.position(), true);
                tile.setPosition(null);
                this.gameControllerFX.gridUpdated();
                event.setDropCompleted(true);
            }
            event.consume();
        });
    }
}
