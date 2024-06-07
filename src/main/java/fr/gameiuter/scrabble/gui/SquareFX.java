package fr.gameiuter.scrabble.gui;

import fr.gameiuter.scrabble.controller.FXGameController;
import fr.gameiuter.scrabble.model.Board;
import fr.gameiuter.scrabble.model.Position;
import fr.gameiuter.scrabble.model.Square;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import static fr.gameiuter.scrabble.gui.TileFX.TILE_SIZE;

public class SquareFX extends Label {
    private final Position position;
    private final Color baseColor;

    public SquareFX(Square square, Position position, FXGameController gameController) {
        this.position = position;
        this.setMinSize(TILE_SIZE, TILE_SIZE);
        this.setMaxSize(TILE_SIZE, TILE_SIZE);
        this.setAlignment(Pos.CENTER);

        this.manageTargetDragAndDrop(gameController);

        int topBorder = 1;
        int rightBorder = 1;
        int bottomBorder = 1;
        int leftBorder = 1;
        if (position.line() == 0) {
            topBorder = 2;
        }
        if (position.column().equals(Board.LAST_LINE_OR_COLUMN)) {
            rightBorder = 2;
        }
        if (position.line().equals(Board.LAST_LINE_OR_COLUMN)) {
            bottomBorder = 2;
        }
        if (position.column() == 0) {
            leftBorder = 2;
        }
        this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(topBorder, rightBorder, bottomBorder, leftBorder))));

        Color backgroundColor = Color.rgb(1, 106, 33);

        switch (square) {
            case START:
                this.setText("✹");
                this.setFont(Font.font(50));
                this.setPadding(new Insets(-11, 0, 0, 0));
                this.setTextOverrun(OverrunStyle.CLIP);
                backgroundColor = Color.rgb(228, 181, 99);
                break;
            case DOUBLE_LETTER:
                this.setText("LD");
                backgroundColor = Color.color(150, 196, 229);
                break;
            case TRIPLE_LETTER:
                this.setText("LT");
                backgroundColor = Color.color(1, 142, 204);
                break;
            case DOUBLE_WORD:
                this.setText("MD");
                backgroundColor = Color.color(228, 181, 99);
                break;
            case TRIPLE_WORD:
                this.setText("MT");
                backgroundColor = Color.color(216, 1, 45);
                break;
            case NORMAL:
                break;
        }
        baseColor = backgroundColor;
        this.setBackground(new Background(new BackgroundFill(backgroundColor, null, null)));
    }

    public void manageTargetDragAndDrop(FXGameController gameController) {
        this.setOnDragOver(event -> {
            if (event.getGestureSource() != this && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
        this.setOnDragEntered(event -> this.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null))));
        this.setOnDragExited(event -> this.setBackground(new Background(new BackgroundFill(baseColor, null, null))));
        this.setOnDragDropped(event -> {
            GridPane boardFX = (GridPane) this.getParent();
            TileFX tileFX = (TileFX) event.getGestureSource();

            boardFX.getChildren().remove(tileFX);
            boardFX.add(tileFX, this.position.column(), this.position.line());

            gameController.removePlacedTilesFX(tileFX.position(), false);
            tileFX.setPosition(this.position());
            gameController.addToPlacedTilesFX(tileFX);
            gameController.gridUpdated();
            event.setDropCompleted(true);
            event.consume();
        });
    }

    public Position position() {
        return this.position;
    }
}
