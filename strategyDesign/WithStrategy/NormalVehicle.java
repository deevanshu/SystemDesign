package com.systemdesign.strategyDesign.WithStrategy;

public class NormalVehicle extends Vehicle{

	public NormalVehicle() {
		super(new NormalDrive());
	}
}
