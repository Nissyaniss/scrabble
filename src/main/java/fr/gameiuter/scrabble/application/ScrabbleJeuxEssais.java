package fr.gameiuter.scrabble.application;

import fr.gameiuter.scrabble.controller.GameController;
import fr.gameiuter.scrabble.model.Board;
import fr.gameiuter.scrabble.model.Player;
import fr.gameiuter.scrabble.model.Tile;

import java.util.List;
import java.util.Optional;

public class ScrabbleJeuxEssais {
    public static void main(String[] args) {
        Player player = new Player("test");
        GameController controller = new GameController(player);

        System.out.println(player.rackDisplay());
        System.out.println(controller);

        for (int i = 0; i < 7; i++) {
            player.getRack().add(controller.getPouch().draw().get());
            System.out.println(player.rackDisplay());
            System.out.println(controller);
        }

        List<Tile> list = player.getRack().getList();
        controller.swap(player, List.of(list.get(6), list.get(1)));
        System.out.println(player.rackDisplay());
        System.out.println(controller);

        Board board = new Board();
        board.placeTile(controller.getPouch().draw().get(), 7, 7);
        System.out.println(board.display());

        Optional<Tile> tile = controller.getPouch().draw();
        while (tile.isPresent()) {
            System.out.println(tile.get());
            tile = controller.getPouch().draw();
        }
    }
}
