package com.systemdesign.DecoratorDesign_Pattern;

public class Main {

	public static void main(String args[]) {

		Bevarage cappacino = new Cappacino();

		System.out.println("cappacino Cost : "+cappacino.cost());

		Bevarage expresso = new Expresso();

		System.out.println("expresso Cost : "+expresso.cost());

		Bevarage milk = new Milk(new Cappacino());

		System.out.println("cappacino With Milk Cost : "+milk.cost());

		
		
		Bevarage cream = new Cream(new Cappacino());

		System.out.println("cappacino With Cream Cost : "+cream.cost());
		
		
		
		cream = new Cream (new Milk(new Cappacino()));
		
		System.out.println("cappacino With Milk & Cream Cost : "+cream.cost());

		
		System.out.println();
		
		milk = new Milk(new Expresso());
		
		System.out.println("expresso With Milk Cost : "+milk.cost());
		
		
		
		cream = new Cream(new Expresso());

		System.out.println("expresso With Cream Cost : "+cream.cost());
		
		
		
		milk = new Cream (new Milk(new Expresso()));
		
		System.out.println("expresso With Milk & Cream Cost : "+milk.cost());


			
	}
}
