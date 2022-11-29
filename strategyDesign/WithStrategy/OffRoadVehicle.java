package com.systemdesign.strategyDesign.WithStrategy;

public class OffRoadVehicle extends Vehicle{

	public OffRoadVehicle() {
		super( new SportsDrive());
		
	}
}
