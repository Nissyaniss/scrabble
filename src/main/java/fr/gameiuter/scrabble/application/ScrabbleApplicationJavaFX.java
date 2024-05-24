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
        FXMLLoader fxmlLoader = new FXMLLoader(ScrabbleApplicationJavaFX.class.getResource("/fr/gameiuter/scrabble/application/Application.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Scrabble");
        stage.setScene(scene);
        stage.show();
    }
}
