package com.systemdesign.strategyDesign.WithStrategy;

public class Vehicle {

	DriveStrategy driveStrategy;

	public Vehicle(DriveStrategy driveStrategy) {
		this.driveStrategy = driveStrategy;
	}

	public void driveCalling() {
		driveStrategy.Drive();
	}
}
