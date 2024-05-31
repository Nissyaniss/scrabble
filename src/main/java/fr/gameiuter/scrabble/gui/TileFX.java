package fr.gameiuter.scrabble.gui;

import fr.gameiuter.scrabble.model.Tile;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class TileFX extends StackPane {
    public static final Integer TILE_SIZE = 50;

    public TileFX(Tile tile) {
        Label letter = new Label();
        Label score = new Label();

        letter.setText(tile.letter().toString().toUpperCase());
        score.setText(String.valueOf(tile.score()));
        score.setAlignment(Pos.BOTTOM_RIGHT);
        score.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        score.setPadding(new Insets(0, 2, 0, 0));

        this.manageSourceDragAndDrop();


        this.setMinSize(TILE_SIZE, TILE_SIZE);
        this.setMaxSize(TILE_SIZE, TILE_SIZE);
        this.getChildren().addAll(letter, score);
        this.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
        this.setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
    }

    public void manageSourceDragAndDrop() {
        this.setOnDragDetected(e -> {
            Dragboard db = this.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString("");
            db.setContent(content);
        });
    }

}
