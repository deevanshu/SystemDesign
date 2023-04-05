package com.java.designpatterns;

class vehicle {
	
	private String engineName;
	private int wheel;
	private int airbags;
	
	public String getEngineName() {
		return engineName;
	}

	public int getWheel() {
		return wheel;
	}

	public int getAirbags() {
		return airbags;
	}
	
	private vehicle(VehicleBuilder builder) {

		this.airbags   = builder.airbags;
		this.engineName= builder.engineName;
		this.wheel     = builder.wheel;
	}
	
	public String toString() {
		return "vehicle [engineName=" + engineName + ", wheel=" + wheel + ", airbags=" + airbags + "]";
	}
	
	public static class VehicleBuilder{

		private String engineName;
		private int wheel;
		private int airbags;

		public VehicleBuilder(String engineName, int wheel) {
			this.engineName = engineName;
			this.wheel = wheel;
		}

		public VehicleBuilder setAirbags(int airbags) {
			this.airbags = airbags;
			return this;
		}

		public vehicle build() {
			
			vehicle v = new vehicle(this);
			return v;
		}
	}	
}

public class BuilderDesign{
	
	public static void main(String args[]) {
		
		// IF non static inner class is there ->
		
		// vehicle vehTwoWheeler =  new vehicle() ;
		// vehicle vehTwoWheelerinner  = vehTwoWheeler.new VehicleBuilder("TwoWheeler", 2).build();
		
		
		vehicle vehTwoWheeler = new vehicle.VehicleBuilder("TwoWheeler", 2).build();
		vehicle vehFourWheeler = new vehicle.VehicleBuilder("FourWheeler", 4).setAirbags(2).build();
		
		System.out.println(vehFourWheeler.getAirbags());
		System.out.println(vehTwoWheeler.getAirbags());
	}
}