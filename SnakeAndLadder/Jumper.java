package com.systemdesign.SnakeAndLadder;

public class Jumper {

	int startPoint ;
	int endPoint ;
	String jumperType ;

	public int getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(int startPoint) {

		this.startPoint = startPoint;

	}
	public int getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(int endPoint) throws Exception {

		if( this.jumperType.equalsIgnoreCase("Snake") && ( endPoint > this.startPoint) ) {

			throw new Exception("Start Point Can't Be Less Than End Point For Snake");
		}
		else if(this.jumperType.equalsIgnoreCase("Snake") && ( this.endPoint < startPoint) ){

			this.endPoint = endPoint;
		}

		else if( this.jumperType.equalsIgnoreCase("Ladder") && ( endPoint < this.startPoint) ) {

			throw new Exception("End Point Can't Be Less Than Start Point For Ladder");
		}
		else {

			this.endPoint = endPoint;
		}	
	}

	public String getJumperType() {
		return jumperType;
	}
	public void setJumperType(String jumperType) {
		this.jumperType = jumperType;
	} 
}
