package fr.gameiuter.scrabble.controller;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class FXStartMenuController {
    @FXML
    private TextField player1TextField;

    @FXML
    private TextField player2TextField;

    @FXML
    private Button startButton;

    @FXML
    private Button leaveButton;

    @FXML
    protected void checkName() {
        if (player1TextField.getText().isEmpty() || player2TextField.getText().isEmpty()) {
            startButton.setDisable(true);
        } else {
            startButton.setDisable(false);
        }
    }

    @FXML
    protected void handleStart() throws IOException {
        FXMLLoader game = new FXMLLoader(FXStartMenuController.class.getResource("/fr/gameiuter/scrabble/application/Game.fxml"), null, null, (x) -> new FXGameController(player1TextField.getText(),player2TextField.getText()));
        startButton.getScene().setRoot(game.load());
    }

    @FXML
    protected void quit() {
        Platform.exit();
        System.exit(0);
    }
}
