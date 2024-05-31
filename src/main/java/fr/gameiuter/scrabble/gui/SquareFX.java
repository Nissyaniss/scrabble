package fr.gameiuter.scrabble.gui;

import fr.gameiuter.scrabble.model.Board;
import fr.gameiuter.scrabble.model.Square;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import static fr.gameiuter.scrabble.gui.TileFX.TILE_SIZE;

public class SquareFX extends Label {
    public SquareFX(Square square, int column, int line) {
        this.setMinSize(TILE_SIZE, TILE_SIZE);
        this.setMaxSize(TILE_SIZE, TILE_SIZE);
        this.setAlignment(Pos.CENTER);

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
        this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(topBorder, rightBorder, bottomBorder, leftBorder))));

        Color backgroundColor = Color.color(1. / 255., 106. / 255., 33. / 255.);

        switch (square) {
            case START:
                this.setText("✹");
                this.setFont(Font.font(50));
                this.setPadding(new Insets(-11, 0, 0, 0));
                backgroundColor = Color.color(228. / 255., 181. / 255., 99. / 255.);
                break;
            case DOUBLE_LETTER:
                this.setText("LD");
                backgroundColor = Color.color(150. / 255., 196. / 255., 229. / 255.);
                break;
            case TRIPLE_LETTER:
                this.setText("LT");
                backgroundColor = Color.color(1. / 255., 142. / 255., 204. / 255.);
                break;
            case DOUBLE_WORD:
                this.setText("MD");
                backgroundColor = Color.color(228. / 255., 181. / 255., 99. / 255.);
                break;
            case TRIPLE_WORD:
                this.setText("MT");
                backgroundColor = Color.color(216. / 255., 1. / 255., 45. / 255.);
                break;
            case NORMAL:
                break;
        }
        this.setBackground(new Background(new BackgroundFill(backgroundColor, null, null)));
    }
}
