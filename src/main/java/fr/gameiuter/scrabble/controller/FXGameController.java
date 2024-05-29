package fr.gameiuter.scrabble.controller;

import fr.gameiuter.scrabble.model.Board;
import fr.gameiuter.scrabble.model.Player;
import fr.gameiuter.scrabble.model.Position;
import fr.gameiuter.scrabble.model.Tile;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

public class FXGameController {
    private static final Integer TILE_SIZE = 50;
    private final GameController gameController;
    @FXML
    protected Label labelPlayer1;
    @FXML
    protected HBox rack;
    @FXML
    private GridPane board;

    public FXGameController(String player1) {
        this.gameController = new GameController(new Player(player1));
    }

    @FXML
    protected void initialize() {
        this.labelPlayer1.setText("Joueur 1 : " + gameController.player().name());
        this.generateGridBase();
        gameController.start();
        this.generateRack();
    }

    private void generateGridBase() {
        for (int line = 0; line < Board.SIZE; line++) {
            for (int column = 0; column < Board.SIZE; column++) {
                Label label = new Label();
                label.setMinSize(TILE_SIZE, TILE_SIZE);
                label.setMaxSize(TILE_SIZE, TILE_SIZE);
                label.setAlignment(Pos.CENTER);

                int topBorder = 1;
                int rightBorder = 1;
                int bottomBorder = 1;
                int leftBorder = 1;
                if (line == 0) {
                    topBorder = 2;
                }
                if (column == Board.LAST_LINE_OR_COLUMN) {
                    rightBorder = 2;
                }
                if (line == Board.LAST_LINE_OR_COLUMN) {
                    bottomBorder = 2;
                }
                if (column == 0) {
                    leftBorder = 2;
                }
                label.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(topBorder, rightBorder, bottomBorder, leftBorder))));

                Color backgroundColor = Color.color(1. / 255., 106. / 255., 33. / 255.);
                this.board.add(label, column, line);
                switch (gameController.board().getSquareAt(new Position(column, line))) {
                    case START:
                        label.setText("✹");
                        label.setFont(Font.font(50));
                        label.setPadding(new Insets(-11, 0, 0, 0));
                        backgroundColor = Color.color(228. / 255., 181. / 255., 99. / 255.);
                        break;
                    case DOUBLE_LETTER:
                        label.setText("LD");
                        backgroundColor = Color.color(150. / 255., 196. / 255., 229. / 255.);
                        break;
                    case TRIPLE_LETTER:
                        label.setText("LT");
                        backgroundColor = Color.color(1. / 255., 142. / 255., 204. / 255.);
                        break;
                    case DOUBLE_WORD:
                        label.setText("MD");
                        backgroundColor = Color.color(228. / 255., 181. / 255., 99. / 255.);
                        break;
                    case TRIPLE_WORD:
                        label.setText("MT");
                        backgroundColor = Color.color(216. / 255., 1. / 255., 45. / 255.);
                        break;
                    case NORMAL:
                        break;
                }
                label.setBackground(new Background(new BackgroundFill(backgroundColor, null, null)));
            }
        }
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
            tileFX.setMinSize(TILE_SIZE, TILE_SIZE);
            tileFX.setMaxSize(TILE_SIZE, TILE_SIZE);
            tileFX.getChildren().addAll(letter, score);
            tileFX.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-style: solid; -fx-border-radius: 5px");
            rack.getChildren().add(tileFX);
        }

    }
}
