package fr.gameiuter.scrabble.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ScrabbleApplicationJavaFX extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader startMenu = new FXMLLoader(ScrabbleApplicationJavaFX.class.getResource("/fr/gameiuter/scrabble/application/StartMenu.fxml"));
        Scene scene = new Scene(startMenu.load(), 320, 240);
        stage.setTitle("Scrabble");
        stage.setScene(scene);
        stage.show();
    }
}
