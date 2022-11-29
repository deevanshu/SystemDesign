package com.systemdesign.SnakeAndLadder;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Map;

public class BoardGame {

	private Deque<Player> nextTurn;
	private ArrayList<Jumper>snake;
	private ArrayList<Jumper>ladder;
	private Dice dice;
	private Map<Integer,Integer>playerCurrentPosition;
	int boardSize;


	public BoardGame(Deque<Player> nextTurn, ArrayList<Jumper> snake, ArrayList<Jumper> ladder, Dice dice,

			Map<Integer, Integer> playerCurrentPosition , int boardSize) {

		this.nextTurn = nextTurn;
		this.snake = snake;
		this.ladder = ladder;
		this.dice = dice;
		this.playerCurrentPosition = playerCurrentPosition;
		this.boardSize = boardSize;
	}
	public void PlayGame () {

		boolean firstChance=true;
		while(nextTurn.size()>1) {
			Player player = nextTurn.poll();
			int currentPosition =  playerCurrentPosition.get(player.id);

			if(firstChance==true) {
				System.out.println("Player "+player.name +" Gets First Chance");
			}
			System.out.println("Player "+player.name +" throws the dice ");
			int diceValue = dice.rollDice();
			System.out.println("No. Came from dice "+diceValue);
			int nextCell = currentPosition + diceValue ;

			System.out.println("Current Position Of "+player.name+" "+nextCell);

			if(nextCell == boardSize) {

				System.out.println("Player "+player.name +"Won the match");
				continue;
			}else if(nextCell > boardSize) {

				System.out.println("Player "+player.name +"number exceeds the board size , no movement for " +player.name);
				nextTurn.add(player);
				continue;
			}else {
				int nextPosition = nextCell;

				for(Jumper snake : snake) {

					if(snake.startPoint==nextPosition) {

						nextPosition = snake.endPoint;
						System.out.println("Player "+player.name +"got bitten by snake");
						playerCurrentPosition.put(player.id, nextPosition);
						nextTurn.add(player);
						continue;
					}
				}

				for(Jumper ladder : ladder) {

					if(ladder.startPoint==nextPosition) {

						nextPosition = ladder.endPoint;
						System.out.println("Player "+player.name +"got Ladder");
						nextTurn.addFirst(player);
					}
					if(nextPosition==boardSize) {
						System.out.println("Player "+player.name +"Won The Match");
						nextTurn.pollFirst();
						continue;
					}else {
						playerCurrentPosition.put(player.id, nextPosition);
						nextTurn.add(player);
						continue;
					}
				}
			}
			firstChance = false;
		}
	}
}