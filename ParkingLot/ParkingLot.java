package PARKING__LOT;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot { 

	private String nameOfParkingLot;
	private Address address;
	public List<ParkingFloor> parkingFloors;  // name ,  Map<ParkingSlotType, List<ParkingSlot>> parkingSlots , displayBoard
	private PricingStrategy pricingStrategy;       // weekday  &  weekend
	protected DisplayBoard displayBoard;           // single shared board for all the floors ,Map -> [ TwoWheeler-4 , COmpact - 3,  ....]

	private static ParkingLot parkingLot = null; // singleton pattern

	private ParkingLot(String nameOfParkingLot, Address address, List<ParkingFloor> parkingFloors, PricingStrategy pricingStrategy) {
		this.nameOfParkingLot = nameOfParkingLot;
		this.address = address;
		this.parkingFloors = parkingFloors;
		this.pricingStrategy = pricingStrategy;

		this.displayBoard = new DisplayBoard(ParkingLot.aggregateAllSlots(parkingFloors));
		for (ParkingFloor floor : parkingFloors) {
			floor.displayBoard = this.displayBoard;  // all flours point to same display board
		}
	}
	public static ParkingLot getInstance(String nameOfParkingLot, Address address, List<ParkingFloor> parkingFloors, PricingStrategy strategy) {
		if (parkingLot == null) {
			parkingLot = new ParkingLot(nameOfParkingLot, address, parkingFloors, strategy);
		}
		return parkingLot;
	}
	// helper to combine slots from all floors for initial board
	private static Map<ParkingSlotType, List<ParkingSlot>> aggregateAllSlots(List<ParkingFloor> floors) {
		Map<ParkingSlotType, List<ParkingSlot>> allSlots = new HashMap<>();
		for (ParkingFloor floor : floors) {
			for (Map.Entry<ParkingSlotType, List<ParkingSlot>> entry : floor.getParkingSlots().entrySet()) {
				allSlots.putIfAbsent(entry.getKey(), new ArrayList<>());
				allSlots.get(entry.getKey()).addAll(entry.getValue());
			}
		}
		return allSlots;
	}
	public double scanAndPay(Ticket ticket) {  // At the exit gate
		long endTime = System.currentTimeMillis();
		long duration = (endTime - ticket.getStartTime()) / 1000; // seconds
		boolean removed = false;
		for (ParkingFloor floor : parkingFloors) {
			for (List<ParkingSlot> slots : floor.getParkingSlots().values()) {
				if (slots.contains(ticket.getParkingSlot())) {
					floor.removeVehicleFromSlot(ticket.getParkingSlot());
					removed = true;
					break;
				}
			}
			 if (removed) break;
		}
		return pricingStrategy.calculatePrice(ticket.getParkingSlot().getParkingSlotType(), duration);
	}

	// Return all slots for DisplayBoard
	public Map<ParkingSlotType, List<ParkingSlot>> getAllSlots() {
		Map<ParkingSlotType, List<ParkingSlot>> allSlots = new HashMap<>();
		for (ParkingFloor floor : parkingFloors) {
			for (Map.Entry<ParkingSlotType, List<ParkingSlot>> entry : floor.getParkingSlots().entrySet()) {
				allSlots.putIfAbsent(entry.getKey(), new ArrayList<>());
				allSlots.get(entry.getKey()).addAll(entry.getValue());
			}
		}
		return allSlots;
	}
}

/* ===================== ENTRY & EXIT GATES ===================== */

class EntranceGate { // To issue the parking ticket
	String gateId;
	ParkingLot parkingLot;

	public EntranceGate(String gateId, ParkingLot lot) {
		this.gateId = gateId;
		this.parkingLot = lot;
	}

	public Ticket issueTicket(Vehicle vehicle) {
		Ticket ticket = null ; 
		for (ParkingFloor floor : parkingLot.parkingFloors) {
			ParkingSlot slot = floor.getRelevantSlotForVehicleAndPark(vehicle);
			if (slot != null) 
				ticket = Ticket.createTicket(vehicle, slot);
			    break; 
		}
		
		if (ticket != null) {
			System.out.println("🚗 " + vehicle.getVehicleCategory() 
			+ " entered through Gate: " + gateId 
			+ " | Ticket No: " + ticket.getTicketNumber());
		} else {
			System.out.println("❌ No slot available for vehicle " + vehicle.getVehicleNumber());
		}
		return ticket;
	}
}

class ExitGate {
	String gateId;
	ParkingLot parkingLot;

	public ExitGate(String gateId, ParkingLot lot) {
		this.gateId = gateId;
		this.parkingLot = lot;
	}

	public void processExit(Ticket ticket) {
		if (ticket == null) {
			System.out.println("⚠️ Invalid ticket — cannot process exit.");
			return;
		}
		double price = parkingLot.scanAndPay(ticket);
		System.out.println("💰 Payment of Rs. " + price + " processed at Exit Gate: " + gateId);
	}
}

enum ParkingSlotType {
	TwoWheeler{
		public double getBaseRate(){
			return 0.05;
		}
	},
	Compact{  // Hatchback Type Vehicles 
		public double getBaseRate(){
			return 0.075;
		}
	},
	Medium{ // SUV , Sedans  Type Vehicle
		public double getBaseRate(){
			return 0.09;
		}
	},
	Large{  // Truck Type Vehicle
		public double getBaseRate(){
			return 0.10;
		}
	};
	
	public abstract double getBaseRate(); // can write methods at last only in enum
}

/* ===================== STRATEGY PATTERN For Pricing ===================== */

interface PricingStrategy {
	public double calculatePrice(ParkingSlotType slotType, long duration);
}

class RegularPricingStrategy implements PricingStrategy {
	public double calculatePrice(ParkingSlotType slotType, long duration) {
		return duration * slotType.getBaseRate();
	}
}

class WeekendPricingStrategy implements PricingStrategy {
	public double calculatePrice(ParkingSlotType slotType, long duration) {
		return duration * slotType.getBaseRate() * 1.2; // 20% higher on weekends
	}
}

/* ===================== DISPLAY BOARD ===================== */

class DisplayBoard {    

	Map<ParkingSlotType, Integer> availableSlots = new HashMap<>();

	public DisplayBoard(Map<ParkingSlotType, List<ParkingSlot>> parkingSlots) {
		for (Map.Entry<ParkingSlotType, List<ParkingSlot>> entry : parkingSlots.entrySet()) {
			int availableCount = 0;
			List<ParkingSlot> slots = entry.getValue();
			for (ParkingSlot slot : slots) {
				if (slot.isAvailable) {
					availableCount++;
				}
			}
			availableSlots.put(entry.getKey(), availableCount);
		}
	}
	
	public void update(ParkingSlotType type, boolean isFreed) {
		availableSlots.put(type, availableSlots.get(type) + (isFreed ? 1 : -1)); // not freed(false) then decrease 1 spot for that SlotType
	}
	
	public void showDisplay() {
		System.out.println("📊 Display Board:");
		for (Map.Entry<ParkingSlotType, Integer> entry : availableSlots.entrySet()) {
			System.out.println(entry.getKey() + " → Available: " + entry.getValue());
		}
		System.out.print("-----------------------------------");
	}
}


class Vehicle {

	String vehicleNumber;
	VehicleCategory vehicleCategory;

	public Vehicle(String vehicleNumber, VehicleCategory vehicleCategory) {
		this.vehicleNumber = vehicleNumber;
		this.vehicleCategory = vehicleCategory;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public VehicleCategory getVehicleCategory() {
		return vehicleCategory;
	}
}

enum VehicleCategory {
	TwoWheeler,  // 2 wheeler slot type 
	Hatchback,  // compact slot type
	Sedan,     // medium   slot type
	SUV,      // medium   slot type
	Bus      // large   slot type
} 
class Address {
	String street;
	String block;
	String city;
	String state;
	String country;

	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
}