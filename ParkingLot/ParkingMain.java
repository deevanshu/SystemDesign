package PARKING__LOT;

import java.util.*;

public class ParkingMain {
    public static void main(String[] args) throws InterruptedException {
        String nameOfParkingLot = "Pintosss Parking Lot";

        Address address = new Address();
        address.setCity("Bangalore");
        address.setCountry("India");
        address.setState("KA");

        // Floors with independent slot lists
        ParkingFloor floor1 = new ParkingFloor("F1", createSlots());
        ParkingFloor floor2 = new ParkingFloor("F2", createSlots());

        List<ParkingFloor> parkingFloors = new ArrayList<>();
        parkingFloors.add(floor1);
        parkingFloors.add(floor2);

        PricingStrategy strategy = new RegularPricingStrategy();
        ParkingLot parkingLot = ParkingLot.getInstance(nameOfParkingLot, address, parkingFloors, strategy);

        System.out.println("\n--- Before Entry ---");
        parkingLot.displayBoard.showDisplay();
        
        EntranceGate gateA = new EntranceGate("Gate-A", parkingLot);
        EntranceGate gateB = new EntranceGate("Gate-B", parkingLot);
        ExitGate exitGate1 = new ExitGate("Exit-1", parkingLot);
        ExitGate exitGate2 = new ExitGate("Exit-2", parkingLot);

//        DisplayBoard displayBoard = new DisplayBoard(parkingLot.getAllSlots());
//        displayBoard.showDisplay();

        // Vehicles
        Vehicle v1 = new Vehicle("KA-01-MA-9999", VehicleCategory.Hatchback);
        Vehicle v2 = new Vehicle("KA-02-MA-8888", VehicleCategory.SUV);
        Vehicle v3 = new Vehicle("KA-03-MA-7777", VehicleCategory.Sedan);

        Ticket t1 = gateA.issueTicket(v1);
        Ticket t2 = gateB.issueTicket(v2);
        Ticket t3 = gateA.issueTicket(v3);

        System.out.println("\n--- After Entry ---");
        parkingLot.displayBoard.showDisplay();

        Thread.sleep(5000);

        if (t1 != null) exitGate1.processExit(t1);
        if (t2 != null) exitGate2.processExit(t2);
        if (t3 != null) exitGate1.processExit(t3);

        System.out.println("\n--- After Exit ---");

        System.out.println("\nAll operations completed successfully.");
    }

    private static Map<ParkingSlotType, List<ParkingSlot>> createSlots() {
        Map<ParkingSlotType, List<ParkingSlot>> slots = new HashMap<>();

        slots.put(ParkingSlotType.Compact, Arrays.asList(
            new ParkingSlot("C1", ParkingSlotType.Compact),
            new ParkingSlot("C2", ParkingSlotType.Compact),
            new ParkingSlot("C3", ParkingSlotType.Compact)
        ));

        slots.put(ParkingSlotType.Medium, Arrays.asList(
            new ParkingSlot("M1", ParkingSlotType.Medium),
            new ParkingSlot("M2", ParkingSlotType.Medium)
        ));

        slots.put(ParkingSlotType.Large, Arrays.asList(
            new ParkingSlot("L1", ParkingSlotType.Large),
            new ParkingSlot("L2", ParkingSlotType.Large),
            new ParkingSlot("L3", ParkingSlotType.Large)
        ));

        return slots;
    }
}