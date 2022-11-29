package com.systemdesign.Elevator;

import java.util.TreeSet;

//   REQUIREMENTS

//   Elevator should move up & down.
//   Buttons to go up & down. - inside lift and on floors
//   dispatcher unit algo
//   100 floor building - 20 elevators - then can use Zones ( dividing floors and giving each zone to some elevators)
//   Door
//   Open/Close door
//   Algorithm for lift
//   Min. waiting time 
//   Less Energy Consumed


class Elevator {

	private int currentFloor = 0;
	private Direction currentDirection = Direction.UP;
	private State currentState = State.IDLE;

	private TreeSet<Request> currentJobs = new TreeSet<>();


	/**
	 * up jobs which cannot be processed now so put in pending queue
	 */
	private TreeSet<Request> upPendingJobs = new TreeSet<>();


	/**
	 * down jobs which cannot be processed now so put in pending queue
	 */
	private TreeSet<Request> downPendingJobs = new TreeSet<>();


	public void startElevator(Request request) {

		System.out.println("The Elevator has started functioning");

		if (currentState == State.IDLE) {
			currentState = State.MOVING;
			currentDirection = request.getExternalRequest().getDirectionToGo();
			currentJobs.add(request);
		} 

		else if (currentState == State.MOVING) {

			if (request.getExternalRequest().getDirectionToGo() != currentDirection) {
				addtoPendingJobs(request);
			} else if (request.getExternalRequest().getDirectionToGo() == currentDirection) {
				if (currentDirection == Direction.UP
						&& request.getInternalRequest().getDestinationFloor() < currentFloor) {
					addtoPendingJobs(request);
				} else if (currentDirection == Direction.DOWN
						&& request.getInternalRequest().getDestinationFloor() > currentFloor) {
					addtoPendingJobs(request);
				} else {
					currentJobs.add(request);
				}

			}
		}

		while (true) {

			if (checkIfJob()) {

				if (currentDirection == Direction.UP) {
					Request request1 = currentJobs.pollFirst();
					processUpRequest(request1);
					if (currentJobs.isEmpty()) {
						addPendingDownJobsToCurrentJobs();

					}
				}

				if (currentDirection == Direction.DOWN) {
					Request request1 = currentJobs.pollLast();
					processDownRequest(request1);
					if (currentJobs.isEmpty()) {
						addPendingUpJobsToCurrentJobs();
					}
				}
			}
		}
	}


	private void processUpRequest(Request request) {
		// The elevator is not on the floor where the person has requested it i.e. source floor. So first bring it there.
		int startFloor = currentFloor;
		if (startFloor < request.getExternalRequest().getSourceFloor()) {
			for (int i = startFloor; i <= request.getExternalRequest().getSourceFloor(); i++) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("We have reached floor -- " + i);
				currentFloor = i;
			}
		}
		// The elevator is now on the floor where the person has requested it i.e. source floor. User can enter and go to the destination floor.
		System.out.println("Reached Source Floor--opening door");

		startFloor = currentFloor;

		for (int i = startFloor; i <= request.getInternalRequest().getDestinationFloor(); i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("We have reached floor -- " + i);
			currentFloor = i;
			if (checkIfNewJobCanBeProcessed(request)) {
				break;
			}
		}
	}

	private void processDownRequest(Request request) {

		int startFloor = currentFloor;
		if (startFloor < request.getExternalRequest().getSourceFloor()) {
			for (int i = startFloor; i <= request.getExternalRequest().getSourceFloor(); i++) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("We have reached floor -- " + i);
				currentFloor = i;
			}
		}

		System.out.println("Reached Source Floor--opening door");

		startFloor = currentFloor;

		for (int i = startFloor; i >= request.getInternalRequest().getDestinationFloor(); i--) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("We have reached floor -- " + i);
			currentFloor = i;
			if (checkIfNewJobCanBeProcessed(request)) {
				break;
			}
		}

	}

	private boolean checkIfNewJobCanBeProcessed(Request currentRequest) {
		if (checkIfJob()) {
			if (currentDirection == Direction.UP) {
				Request request = currentJobs.pollFirst();
				if (request.getInternalRequest().getDestinationFloor() < currentRequest.getInternalRequest()
						.getDestinationFloor()) {
					currentJobs.add(request);
					currentJobs.add(currentRequest);
					return true;
				}
				//	addtoPendingJobs(request);
				currentJobs.add(request); //bcz we polled in above line

			}

			if (currentDirection == Direction.DOWN) {
				Request request = currentJobs.pollLast();
				if (request.getInternalRequest().getDestinationFloor() > currentRequest.getInternalRequest()
						.getDestinationFloor()) {
					currentJobs.add(request);
					currentJobs.add(currentRequest);
					return true;
				}
				addtoPendingJobs(request);
				//	currentJobs.add(request);
			}
		}
		return false;

	}

	private void addPendingDownJobsToCurrentJobs() {
		if (!downPendingJobs.isEmpty()) {
			System.out.println("Pick a pending down job and execute it by putting in current job");

			currentJobs = downPendingJobs;
			currentDirection = Direction.DOWN;
		} else {
			currentState = State.IDLE;
			System.out.println("The elevator is in Idle state As there asre no pending Jobs");
		}
	}

	private void addPendingUpJobsToCurrentJobs() {	
		if (!upPendingJobs.isEmpty()) {
			System.out.println("Pick a pending up job and execute it by putting in current job");

			currentJobs = upPendingJobs;
			currentDirection = Direction.UP;
		} else {
			currentState = State.IDLE;
			System.out.println("The elevator is in Idle state As there asre no pending Jobs");

		}}

	public boolean checkIfJob() {

		if (currentJobs.isEmpty()) {
			return false;
		}
		return true;

	}

	public void addtoPendingJobs(Request request) {		

		if (request.getExternalRequest().getDirectionToGo() == Direction.UP) {
			System.out.println("Add to pending up jobs");
			upPendingJobs.add(request);
		} else {
			System.out.println("Add to pending down jobs");
			downPendingJobs.add(request);
		}
	}
}

enum State {
	MOVING, STOPPED, IDLE
}
enum Direction {
	UP, DOWN ;
}
class ExternalRequest {
	private Direction directionToGo;
	private int sourceFloor;

	public ExternalRequest(Direction directionToGo, int sourceFloor) {
		this.directionToGo = directionToGo;
		this.sourceFloor = sourceFloor;
		System.out.println("The Elevator has been requested on floor - " + sourceFloor + " and the person wants go in the - "
				+ directionToGo);
	}
	public Direction getDirectionToGo() {
		return directionToGo;
	}
	public void setDirectionToGo(Direction directionToGo) {
		this.directionToGo = directionToGo;
	}
	public int getSourceFloor() {
		return sourceFloor;
	}
	public void setSourceFloor(int sourceFloor) {
		this.sourceFloor = sourceFloor;
	}

}

class InternalRequest {
	private int destinationFloor;

	public InternalRequest(int destinationFloor) {
		this.destinationFloor = destinationFloor;
		System.out.println("The destinationFloor is - " + destinationFloor);
	}
	public int getDestinationFloor() {
		return destinationFloor;
	}
	public void setDestinationFloor(int destinationFloor) {
		this.destinationFloor = destinationFloor;
	}
	public String toString() {
		return "The destinationFloor is - " + destinationFloor;
	}
}

class Request  implements Comparable<Request>{

	private InternalRequest internalRequest;
	private ExternalRequest externalRequest;

	public Request(InternalRequest internalRequest, ExternalRequest externalRequest) {
		this.internalRequest = internalRequest;
		this.externalRequest = externalRequest;
	}
	public InternalRequest getInternalRequest() {
		return internalRequest;
	}
	public void setInternalRequest(InternalRequest internalRequest) {
		this.internalRequest = internalRequest;
	}
	public ExternalRequest getExternalRequest() {
		return externalRequest;
	}
	public void setExternalRequest(ExternalRequest externalRequest) {
		this.externalRequest = externalRequest;
	}
	@Override
	public int compareTo(Request o) {
		return 0;
	}
}
public class ElevatorMain {

	public static void main(String args[]) {
		Elevator elevator = new Elevator();
		ExternalRequest er = new ExternalRequest(Direction.UP, 0);

		InternalRequest ir = new InternalRequest(5);
		Request request1 = new Request(ir, er);

		//elevator.addJob(request1);
		elevator.startElevator(request1);

	}
}
