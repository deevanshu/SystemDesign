package com.systemdesign.SnakeAndLadder;

public class Dice {

	int numberofDice ; 
	
	public Dice(int numberofDice) {
		
		this.numberofDice=numberofDice;
	}
	
	public int rollDice(){
		
		return (int )( Math.random() * (numberofDice*6  - numberofDice*1) + numberofDice*1);
	}
}
