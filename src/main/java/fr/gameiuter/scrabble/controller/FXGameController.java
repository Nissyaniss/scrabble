package fr.gameiuter.scrabble.controller;

import fr.gameiuter.scrabble.gui.Console;
import fr.gameiuter.scrabble.gui.RackFX;
import fr.gameiuter.scrabble.gui.SquareFX;
import fr.gameiuter.scrabble.gui.TileFX;
import fr.gameiuter.scrabble.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class FXGameController {
    private final GameController gameController;
    private final HashMap<Position, TileFX> placedTilesFX;
    private RackFX rackFX;

    @FXML
    private Button confirm;
    @FXML
    private Button toggleModeButton;
    @FXML
    private Label labelPlayer1;
    @FXML
    private Label player1Score;
    @FXML
    private Label labelPlayer2;
    @FXML
    private Label player2Score;
    @FXML
    private HBox rack;
    @FXML
    private GridPane board;
    @FXML
    private Label labelTours;
    @FXML
    private Label labelJoueur;
    @FXML
    private Button randomButton;
    @FXML
    private Label errorLabel;

    public FXGameController(String player1, String player2) {
        this.gameController = new GameController(new Player(player1), new Player(player2));
        this.placedTilesFX = new HashMap<>();
    }

    @FXML
    protected void initialize() {
        this.labelPlayer1.setText("Joueur 1 : " + this.gameController.player1().name());
        this.labelPlayer2.setText("Joueur 2 : " + this.gameController.player2().name());
        this.generateTurn();
        this.generateGridBase();
        this.gameController.start();
        this.updateScores();
        this.rackFX = new RackFX(rack, gameController, toggleModeButton, confirm, this, randomButton, board);
    }

    @FXML
    protected void handleConfirm() {
        if (this.rackFX.getMode().equals(FXControllerMode.PLACE_WORD)) {
            Player player = this.gameController.player();
            Map<Position, TileFX> newTilesFX = this.placedTilesFX
                    .entrySet()
                    .stream()
                    .filter(entry -> !entry.getValue().isFrozen())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            Direction wordDirection = this.getDirection(newTilesFX.keySet().iterator().next());
            Map<Position, Tile> newTiles = new HashMap<>();

            for (Map.Entry<Position, TileFX> entry : newTilesFX.entrySet()) {
                newTiles.put(entry.getKey(), entry.getValue().tile());
            }

            int score = this.gameController.computeScore(newTiles, wordDirection);
            player.incrementScore(score);

            for (TileFX tileFX : newTilesFX.values()) {
                tileFX.freeze();
                player.rack().remove(tileFX.tile());
                this.gameController.board().placeTile(tileFX.tile(), tileFX.position().column(), tileFX.position().line());
            }

            this.gameController.draw(player);
            if (player.rack().tiles().isEmpty()) {
                this.endGame();
            }
            this.generateTurn();
            this.confirm.setDisable(true);
            this.rackFX.refreshRack();
            this.updateScores();
        } else {
            List<Tile> tilesToSwap = new ArrayList<>();
            for (Node node : this.rack.getChildren()) {
                TileFX tileFX = (TileFX) node;
                if (tileFX.marked()) {
                    tilesToSwap.add(tileFX.tile());
                }
            }
            this.rack.getChildren().clear();

            this.gameController.swap(this.gameController.player(), tilesToSwap);
            this.generateTurn();
            this.rackFX.refreshRack();
            this.rackFX.setMode(FXControllerMode.PLACE_WORD);
        }

        this.gameController.player().normalPlay();
        this.swapTurn();
    }

    @FXML
    protected void toggleMode() {
        if (this.rackFX.getMode().equals(FXControllerMode.PLACE_WORD)) {
            this.rackFX.setMode(FXControllerMode.SWAP_LETTERS);
            this.resetPlacedTiles();
            for (Node node : this.rack.getChildren()) {
                TileFX tile = (TileFX) node;
                tile.freeze();
                tile.setMarkable(true);
            }
        } else {
            this.rackFX.setMode(FXControllerMode.PLACE_WORD);
            for (Node node : this.rack.getChildren()) {
                TileFX tile = (TileFX) node;
                tile.unfreeze();
                tile.unmark();
                tile.setMarkable(false);
            }
        }
    }

    @FXML
    protected void randomizeRack() {
        Collections.shuffle(this.gameController.player().rack().tiles());
        this.rackFX.refreshRack();
    }

    private void generateGridBase() {
        for (int line = 0; line < Board.SIZE; line++) {
            for (int column = 0; column < Board.SIZE; column++) {
                Square square = this.gameController.board().getSquareAt(new Position(column, line));
                SquareFX squareFX = new SquareFX(square, new Position(column, line), this);
                this.board.add(squareFX, column, line);
            }
        }
    }

    // cannot be called if checkPlacement() returns false
    private boolean isExistingWord() {
        Map<Position, Tile> placedTiles = this.placedTilesFX
                .entrySet()
                .stream()
                .filter(entry -> !entry.getValue().isFrozen())
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().tile()));

        Map.Entry<Position, Tile> entry = placedTiles.entrySet().iterator().next();
        Position pos = entry.getKey();
        Direction direction = this.getDirection(pos);
        Word word = this.gameController.board().getWordAt(pos, this.getDirection(pos), placedTiles);
        // when there is only one new tile on the board, this.getDirection will default to horizontal,
        // so we use vertical if there is no other letters in the horizontal direction
        if (placedTiles.size() == 1 && word.tiles().size() == 1) {
            direction = Direction.VERTICAL;
        }
        return this.gameController.areAllExistingWord(placedTiles, pos, direction);
    }

    private boolean checkPlacement() {
        if (this.placedTilesFX.get(new Position(Board.MIDDLE, Board.MIDDLE)) == null) {
            return false;
        }

        Map<Position, TileFX> newTilesFX = this.placedTilesFX
                .entrySet()
                .stream()
                .filter(entry -> !entry.getValue().isFrozen())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (newTilesFX.isEmpty()) {
            return false;
        }

        Map<Position, Tile> newTiles = new HashMap<>();
        for (Map.Entry<Position, TileFX> entry : newTilesFX.entrySet()) {
            newTiles.put(entry.getKey(), entry.getValue().tile());
        }

        Position somePosition = newTilesFX.keySet().iterator().next();
        Direction direction = this.getDirection(somePosition);
        if (direction == null) {
            return false;
        }

        Word someWord = this.gameController.board().getWordAt(somePosition, direction, newTiles);
        if (newTilesFX.size() == 1 && someWord.tiles().size() == 1) {
            someWord = this.gameController.board().getWordAt(somePosition, Direction.VERTICAL, newTiles);
        }

        if (someWord.tiles().size() == 1) {
            return false;
        }
        return someWord.tiles().containsAll(newTiles.values());
    }

    private Direction getDirection(Position position) {
        boolean isVertical = false;
        for (TileFX tile : this.placedTilesFX.values()) {
            if (!tile.isFrozen() && !tile.position().line().equals(position.line())) {
                isVertical = true;
                if (!tile.position().column().equals(position.column()))
                    return null;
            }
        }

        if (isVertical)
            return Direction.VERTICAL;
        else
            return Direction.HORIZONTAL;
    }

    public void addToPlacedTilesFX(TileFX tileFX) {
        if (tileFX.tile().isJoker() && tileFX.tile().letter() == '*') {
            char letter;
            while (true) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setHeaderText("Quelle lettre voulez-vous que ce soit ?");
                Optional<String> text = dialog.showAndWait();
                if (text.isPresent() && text.get().length() == 1 && Character.isAlphabetic(text.get().charAt(0))) {
                    letter = text.get().charAt(0);
                    break;
                }
            }
            tileFX.setLetter(letter);
        }
        this.placedTilesFX.put(tileFX.position(), tileFX);
    }

    public void removePlacedTilesFX(Position position, boolean resetJoker) {
        TileFX tileFX = this.placedTilesFX.remove(position);
        if (resetJoker && tileFX != null && tileFX.tile().isJoker()) {
            tileFX.setLetter('*');
        }
    }

    private void resetPlacedTiles() {
        List<Position> positionsToRemove = new ArrayList<>();
        for (Map.Entry<Position, TileFX> entry : this.placedTilesFX.entrySet()) {
            TileFX tile = entry.getValue();
            if (!tile.isFrozen()) { // frozen tiles are the ones that were already played
                this.board.getChildren().remove(tile);
                this.rack.getChildren().add(tile);
                positionsToRemove.add(entry.getKey());
            }
        }
        for (Position position : positionsToRemove) {
            TileFX tileFX = this.placedTilesFX.remove(position);
            if (tileFX.tile().isJoker()) {
                tileFX.setLetter('*');
            }
        }
    }

    private void updateScores() {
        this.player1Score.setText("Score: " + this.gameController.player1().score());
        this.player2Score.setText("Score: " + this.gameController.player2().score());
    }

    private void generateTurn() {
        this.labelTours.setText("Tours : " + this.gameController.getTurn());
        this.labelJoueur.setText("Joueur actuel : " + gameController.player().name());
    }

    @FXML
    private void skipTurn() {
        this.resetPlacedTiles();

        Player player = this.gameController.player();
        player.skipTurn();
        if (player.consecutiveSkippedTurns() >= 3) {
            this.endGame();
        }
        this.swapTurn();
    }

    private void swapTurn() {
        this.gameController.increaseTurn();
        this.updateScores();
        this.generateTurn();
        this.rackFX.refreshRack();
    }

    public void gridUpdated() {
        boolean checkPlacement = this.checkPlacement();
        if (!checkPlacement)
            this.errorLabel.setText("Le mot placé ne respecte pas les règles de placement !");
        else if (!this.isExistingWord())
            this.errorLabel.setText("Un mot placé n'est pas dans le dictionnaire !");
        else
            this.errorLabel.setText("");
        this.confirm.setDisable(!(checkPlacement && this.isExistingWord()));
    }

    private void endGame() {
        Player player;
        if (this.gameController.player1().score() > this.gameController.player2().score()) {
            player = this.gameController.player1();
        } else if (this.gameController.player1().score() < this.gameController.player2().score()) {
            player = this.gameController.player2();
        } else {
            player = null;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fin de la partie");

        if (player == null) {
            alert.setContentText("C'est une égalité !");
        } else {
            alert.setContentText(player.name() + " a gagné la partie !");
        }

        ButtonType buttonNew = new ButtonType("Nouvelle partie");
        ButtonType buttonQuit = new ButtonType("Quitter");
        alert.getButtonTypes().setAll(buttonNew, buttonQuit);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonNew) {
            String p1Name = this.gameController.player1().name();
            String p2Name = this.gameController.player2().name();
            FXMLLoader game = new FXMLLoader(
                    FXStartMenuController.class.getResource("/fr/gameiuter/scrabble/application/Game.fxml"),
                    null,
                    null,
                    x -> new FXGameController(p1Name, p2Name)
            );
            try {
                confirm.getScene().setRoot(game.load());
            } catch (IOException e) {
                Console.message("Cannot load new game: " + e.getMessage());
            }
        } else {
            Platform.exit();
        }
    }
}
