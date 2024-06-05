package fr.gameiuter.scrabble.gui;

import fr.gameiuter.scrabble.controller.FXGameController;
import fr.gameiuter.scrabble.model.Position;
import fr.gameiuter.scrabble.model.Tile;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Shear;

import java.util.function.Consumer;

public class TileFX extends StackPane {
    public static final Integer TILE_SIZE = 50;
    public static final TileFX NO = null;
    private static final Color BASE_COLOR = Color.WHEAT;

    private final Label letter;
    private final Tile tile;
    private Position position;
    private boolean frozen;
    private boolean markable;
    private boolean marked;
    private Consumer<Boolean> onMarkChangedCallback;

    public TileFX(Tile tile, HBox rack, FXGameController gameController) {
        this.tile = tile;
        this.frozen = false;

        this.letter = new Label();
        Label score = new Label();

        Shear shear = new Shear();
        shear.setX(-0.1763); // -10 degrees in radians

        if (tile.letter() != '*') {
            this.letter.setText(tile.letter().toString().toUpperCase());
        }
        this.letter.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        this.letter.getTransforms().add(shear);

        score.setText(String.valueOf(tile.score()));
        score.setAlignment(Pos.BOTTOM_RIGHT);
        score.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        score.setPadding(new Insets(0, -3, 0, 0));
        score.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        score.getTransforms().add(shear);

        this.setMinSize(TILE_SIZE, TILE_SIZE);
        this.setMaxSize(TILE_SIZE, TILE_SIZE);
        this.getChildren().addAll(letter, score);
        this.setBackground(new Background(new BackgroundFill(BASE_COLOR, new CornerRadii(7), null)));

        this.setOnMouseClicked(e -> this.toggleMark());
        this.setOnDragDetected(e -> {
            if (!this.frozen) {
                Dragboard db = this.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString("");
                db.setContent(content);
            }
        });
        this.setOnDragOver(event -> {
            Node source = (Node) event.getGestureSource();
            if (source != this && this.getParent().equals(rack)) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
        });
        this.setOnDragDropped(event -> {
            TileFX source = (TileFX) event.getGestureSource();
            if (source != this && this.getParent().equals(rack)) {
                ((Pane) source.getParent()).getChildren().remove(source);
                int index = rack.getChildren().indexOf(this);

                if (event.getX() < TILE_SIZE / 2.) {
                    rack.getChildren().add(index, source);
                } else {
                    rack.getChildren().add(index + 1, source);
                }

                gameController.removePlacedTilesFX(source.position(), true);
                source.setPosition(null);
                gameController.gridUpdated();
                event.setDropCompleted(true);
            } else {
                event.setDropCompleted(false);
            }
        });
    }

    public Tile tile() {
        return this.tile;
    }

    public void setLetter(char letter) {
        this.tile.setLetter(letter);
        if (letter == '*') {
            this.letter.setText("");
        } else {
            this.letter.setText(String.valueOf(letter).toUpperCase());
        }
    }

    public Position position() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void freeze() {
        this.frozen = true;
    }

    public void unfreeze() {
        this.frozen = false;
    }

    public boolean isFrozen() {
        return this.frozen;
    }

    public void setMarkable(boolean markable) {
        this.markable = markable;
    }

    public boolean marked() {
        return this.marked;
    }

    private void toggleMark() {
        if (this.markable) {
            if (this.marked) {
                this.unmark();
            } else {
                this.setBackground(new Background(new BackgroundFill(Color.GOLD, new CornerRadii(7), null)));
                this.marked = true;
            }
            if (this.onMarkChangedCallback != null) {
                this.onMarkChangedCallback.accept(!this.marked);
            }
        }
    }

    public void unmark() {
        this.setBackground(new Background(new BackgroundFill(BASE_COLOR, new CornerRadii(7), null)));
        this.marked = false;
    }

    public void setOnMarkChanged(Consumer<Boolean> callback) {
        this.onMarkChangedCallback = callback;
    }
}
