package fr.gameiuter.scrabble.gui;

import fr.gameiuter.scrabble.model.Tile;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class TileFX extends StackPane {
    public static final Integer TILE_SIZE = 50;

    public TileFX(Tile tile) {
        Label letter = new Label();
        Label score = new Label();

        letter.setText(tile.letter().toString().toUpperCase());
        score.setText(String.valueOf(tile.score()));
        score.setAlignment(Pos.BOTTOM_RIGHT);
        score.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        this.setMinSize(TILE_SIZE, TILE_SIZE);
        this.setMaxSize(TILE_SIZE, TILE_SIZE);
        this.getChildren().addAll(letter, score);
        this.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-style: solid; -fx-border-radius: 5px");
    }
}
