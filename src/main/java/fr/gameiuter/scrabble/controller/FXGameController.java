package fr.gameiuter.scrabble.controller;

import fr.gameiuter.scrabble.model.Player;
import fr.gameiuter.scrabble.model.Tile;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.List;

public class FXGameController {
    private GameController gameController;

    @FXML
    protected Label labelPlayer1;

    @FXML
    protected HBox rack;

    public FXGameController(String player1) {
        this.gameController = new GameController(new Player(player1));
    }

    @FXML
    private GridPane board;

    @FXML
    protected void initialize() {
        this.labelPlayer1.setText("Joueur 1 : " + gameController.player().name());
        this.generateGridBase();
        gameController.start();
        this.generateRack();
    }

    private void generateGridBase() {

    }

    private void generateRack() {
        List<Tile> rackTile = gameController.player().rack().tiles();
        rack.getChildren().clear();
        for (Tile tile : rackTile) {
            Label letter = new Label();
            Label score = new Label();
            StackPane tileFX = new StackPane();
            letter.setText(tile.letter().toString().toUpperCase());
            score.setText(String.valueOf(tile.score()));
            score.setAlignment(Pos.BOTTOM_RIGHT);
            score.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            tileFX.setMinWidth(50);
            tileFX.setMaxHeight(50);
            tileFX.getChildren().addAll(letter, score);
            tileFX.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-style: solid; -fx-border-radius: 5px");
            rack.getChildren().add(tileFX);
        }

    }
}
