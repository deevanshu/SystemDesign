package com.systemdesign.strategyDesign.WithStrategy;

public class Main {

	public static void main(String args[]) {

		Vehicle veh = new NormalVehicle();
		veh.driveCalling();

		Vehicle veh1 = new SportsVehicle();
		veh1.driveCalling();

		Vehicle veh2 = new OffRoadVehicle();
		veh2.driveCalling();

		// Strategy pattern used when more than 1 child class using same redundant code and which is not present 
		//in the base class that they are extending
	}
}
