package fr.gameiuter.scrabble.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class FXGameController {
    private GameController gameController;

    @FXML
    private GridPane board;

    @FXML
    protected void initialize() {
        this.gameController = new GameController();
        this.generateGridBase();
    }

    private void generateGridBase() {

    }
}
