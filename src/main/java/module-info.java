module fr.unilim.scrabble {
    requires javafx.controls;
    requires javafx.fxml;


    opens fr.unilim.scrabble to javafx.fxml;
    exports fr.unilim.scrabble;
}