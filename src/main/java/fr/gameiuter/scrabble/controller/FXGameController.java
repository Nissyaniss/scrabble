package fr.gameiuter.scrabble.controller;

import fr.gameiuter.scrabble.model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class FXGameController {
    private GameController gameController;

    @FXML
    protected Label labelPlayer1;

    public FXGameController(String player1) {
        this.gameController = new GameController(new Player(player1));
    }

    @FXML
    private GridPane board;

    @FXML
    protected void initialize() {
        this.labelPlayer1.setText("Joueur 1 : " + gameController.player().name());
        this.generateGridBase();
    }

    private void generateGridBase() {

    }
}
