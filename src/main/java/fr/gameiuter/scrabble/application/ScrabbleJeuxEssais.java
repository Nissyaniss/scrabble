package fr.gameiuter.scrabble.application;

import fr.gameiuter.scrabble.controller.GameController;
import fr.gameiuter.scrabble.model.Player;
import fr.gameiuter.scrabble.model.Tile;

import java.util.List;

public class ScrabbleJeuxEssais {
    public static void main(String[] args) {
        GameController controller = new GameController();
        Player player = new Player("test");

        System.out.println(player.rackDisplay());
        System.out.println(controller);

        for (int i = 0; i < 7; i++) {
            player.getRack().add(controller.getPouch().draw());
            System.out.println(player.rackDisplay());
            System.out.println(controller);
        }

        List<Tile> list = player.getRack().getList();
        controller.swap(player, new Tile[]{list.get(6), list.get(1)});
        System.out.println(player.rackDisplay());
        System.out.println(controller);

        Tile tile;
        do {
            tile = controller.getPouch().draw();
            System.out.println(tile);
        } while (tile != null);
    }
}
