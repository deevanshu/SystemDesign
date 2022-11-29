package com.systemdesign.SnakeAndLadder;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

// Multiple Players  
// Mutiple Dice 
// Mutiple Snakes and Ladders 
// GameBoard with all configs loaded and contains game play method
// Configurable board size 
// Main Class to set configurations 


public class SnakeAndLadderMain {

	public static void main(String args[]) throws Exception {

		System.out.println("Enter No. Of Players");
		Scanner sc  = new Scanner(System.in);
		int numberOfPlayers = sc.nextInt();
		Deque <Player> nextturn = new LinkedList<>();

		HashMap <Integer , Integer> hm = new HashMap<>();
		for(int i=0 ; i<numberOfPlayers ; i++) {

			System.out.println("Enter Id for Player"+ (i+1));
			int id = sc.nextInt();
			System.out.println("Enter Name for Player"+ (i+1));
			String name = sc.next();

			nextturn.add(new Player(id , name));
			hm.put(id, 0);
		}
		ArrayList <Jumper> snakes = new ArrayList<>() ;
		System.out.println("Enter No. Of Snakes");
		int numberOfSnakes = sc.nextInt();

		for(int i=0 ; i<numberOfSnakes ; i++) {

			System.out.println("Enter Start Position And End Position For Snake"+ (i+1));
			int startPos = sc.nextInt();
			int endPos = sc.nextInt();
			String name="Snake";

			Jumper j = new Jumper();

			j.setJumperType(name);
			j.setStartPoint(startPos);
			j.setEndPoint(endPos);

			snakes.add(j);
		}

		ArrayList <Jumper> ladders = new ArrayList<>() ;
		System.out.println("Enter No. Of Ladders");
		int numberOfLadders = sc.nextInt();
		try {
			for(int i=0 ; i<numberOfLadders ; i++) {

				System.out.println("Enter Start Position And End Position For Ladder"+ i+1);
				int startPos = sc.nextInt();
				int endPos = sc.nextInt();
				String name="Ladder";

				Jumper j = new Jumper();

				j.setJumperType(name);
				j.setStartPoint(startPos);
				j.setEndPoint(endPos);

				ladders.add(j);
			}
		}
		catch(Exception e) {

			System.out.println("Exception Occured " + e);
		}

		System.out.println("Enter No. Of Dices");
		int dic = sc.nextInt();
		Dice dice = new Dice(dic);

		System.out.println("Enter board Size");
		int boardSize = sc.nextInt();
		BoardGame gm = new BoardGame(nextturn, snakes, ladders, dice, hm, boardSize);
		gm.PlayGame();
	}
}