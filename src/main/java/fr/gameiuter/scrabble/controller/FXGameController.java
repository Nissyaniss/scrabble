package fr.gameiuter.scrabble.controller;

import fr.gameiuter.scrabble.gui.SquareFX;
import fr.gameiuter.scrabble.gui.TileFX;
import fr.gameiuter.scrabble.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FXGameController {
    private final GameController gameController;
    private List<TileFX> placedTilesFX = new ArrayList<>();

    @FXML
    protected Label labelPlayer1;
    @FXML
    protected HBox rack;
    @FXML
    private GridPane board;
    @FXML
    public Button confirm;

    public FXGameController(String player1) {
        this.gameController = new GameController(new Player(player1));
    }

    @FXML
    protected void handleConfirm() {
        if (placedTilesFX.size() == 0) {
            System.out.println("test");
            return;
        } else {
            Map<Position, Tile> placedTiles = new HashMap<>();
            for (TileFX tileFX : placedTilesFX) {
                placedTiles.put(tileFX.position(), tileFX.tile());
            }
            System.out.println(gameController.computeScore(placedTiles, Direction.HORIZONTAL));
        }
    }


    @FXML
    protected void initialize() {
        this.labelPlayer1.setText("Joueur 1 : " + this.gameController.player().name());
        this.generateGridBase();
        this.gameController.start();
        this.generateRack();
    }

    private void generateGridBase() {
        for (int line = 0; line < Board.SIZE; line++) {
            for (int column = 0; column < Board.SIZE; column++) {
                Square square = gameController.board().getSquareAt(new Position(column, line));
                SquareFX squareFX = new SquareFX(square, new Position(column, line), this);
                this.board.add(squareFX, column, line);
            }
        }
    }

    private void generateRack() {
        List<Tile> rackTile = gameController.player().rack().tiles();
        rack.getChildren().clear();
        for (Tile tile : rackTile) {
            TileFX tileFX = new TileFX(tile);
            rack.getChildren().add(tileFX);
        }
    }

    public void addToPlacedTilesFX(TileFX tile) {
        this.placedTilesFX.add(tile);
    }
}
