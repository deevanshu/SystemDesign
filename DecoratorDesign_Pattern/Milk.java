package com.systemdesign.DecoratorDesign_Pattern;

public class Milk extends BevarageDecorator{

	Bevarage bevarage;
	
	public Milk(Bevarage bevarage) {
		
		this.bevarage = bevarage;
	}
	@Override
	public int cost() {
		
		return bevarage.cost() + 10;
	}

}
