package com.systemdesign.DecoratorDesign_Pattern;

public class Cream extends BevarageDecorator{

	Bevarage bevarage;

	public Cream(Bevarage bevarage) {

		this.bevarage = bevarage;
	}
	@Override
	public int cost() {

		return bevarage.cost() + 20;
	}
}
