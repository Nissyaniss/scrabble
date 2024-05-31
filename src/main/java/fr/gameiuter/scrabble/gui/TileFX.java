package fr.gameiuter.scrabble.gui;

import fr.gameiuter.scrabble.model.Position;
import fr.gameiuter.scrabble.model.Tile;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.function.Consumer;

public class TileFX extends StackPane {
    public static final Integer TILE_SIZE = 50;
    private static Color BASE_COLOR = Color.BEIGE;

    private Tile tile;
    private Position position;
    private boolean frozen;
    private boolean markable;
    private boolean marked;
    private Consumer<Boolean> onMarkChangedCallback;

    public TileFX(Tile tile) {
        this.tile = tile;
        this.frozen = false;

        Label letter = new Label();
        Label score = new Label();

        letter.setText(tile.letter().toString().toUpperCase());
        score.setText(String.valueOf(tile.score()));
        score.setAlignment(Pos.BOTTOM_RIGHT);
        score.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        score.setPadding(new Insets(0, 2, 0, 0));

        this.setMinSize(TILE_SIZE, TILE_SIZE);
        this.setMaxSize(TILE_SIZE, TILE_SIZE);
        this.getChildren().addAll(letter, score);
        this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
        this.setBackground(new Background(new BackgroundFill(BASE_COLOR, null, null)));

        this.setOnDragDetected(e -> {
            if (!this.frozen) {
                Dragboard db = this.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString("");
                db.setContent(content);
            }
        });
        this.setOnMouseClicked(e -> this.toggleMark());
    }

    public Tile tile() {
        return this.tile;
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
                this.setBackground(new Background(new BackgroundFill(Color.GOLD, null, null)));
                this.marked = true;
            }
            if (this.onMarkChangedCallback != null) {
                this.onMarkChangedCallback.accept(!this.marked);
            }
        }
    }

    public void unmark() {
        this.setBackground(new Background(new BackgroundFill(BASE_COLOR, null, null)));
        this.marked = false;
    }

    public void setOnMarkChanged(Consumer<Boolean> callback) {
        this.onMarkChangedCallback = callback;
    }
}
