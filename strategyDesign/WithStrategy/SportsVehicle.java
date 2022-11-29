package com.systemdesign.strategyDesign.WithStrategy;

public class SportsVehicle extends Vehicle{

	public SportsVehicle() {
		super( new SportsDrive());
		
	}
}
