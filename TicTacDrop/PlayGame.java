package com.systemdesign.TicTacDrop;

import java.util.LinkedList;
import java.util.Queue;

public class PlayGame {
    public static void main(String[] args) {
        Player p1 = new Player();
        p1.setPlayerId(0);
        
        p1.setPlayerId(1);
        p1.setPlayerName("Divs");
        p1.setPlayerSymbol('X');

        Player p2 = new Player();
        p2.setPlayerId(2);
        p2.setPlayerName("Arora");
        p2.setPlayerSymbol('O');
        
        Queue <Player>players1 = new LinkedList<>();
        players1.add(p1);
        players1.add(p2);
        GameBoard gb = new GameBoard(3,players1);
        
        gb.startGame();
    }
}
