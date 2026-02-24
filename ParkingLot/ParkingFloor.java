package PARKING__LOT;

import java.util.List;
import java.util.Map;

public class ParkingFloor {
    String name;
    Map<ParkingSlotType, List<ParkingSlot>> parkingSlots; // [Compact -> {C1 , C2 , C3}, Large -> {L1 , L2 , L3} ]
    DisplayBoard displayBoard;

    public ParkingFloor(String name, Map<ParkingSlotType, List<ParkingSlot>> parkingSlots) {
        this.name = name;
        this.parkingSlots = parkingSlots;
    }

    public void setDisplayBoard(DisplayBoard displayBoard) {
        this.displayBoard = displayBoard;
    }
    
    public Map<ParkingSlotType, List<ParkingSlot>> getParkingSlots() {
        return parkingSlots;
    }

    public ParkingSlot getRelevantSlotForVehicleAndPark(Vehicle vehicle) {
        VehicleCategory category = vehicle.getVehicleCategory();
        ParkingSlotType slotType = getParkingSlotType(category);

        List<ParkingSlot> slots = parkingSlots.get(slotType);
        if (slots == null) return null;

        for (ParkingSlot slot : slots) {
            if (slot.isAvailable) {
                slot.addVehicle(vehicle);
                displayBoard.update(slotType, false); // update board
                displayBoard.showDisplay();
                return slot;
            }
        }
        return null;
    }

    public void removeVehicleFromSlot(ParkingSlot slot) {
        slot.removeVehicle();
        displayBoard.update(slot.getParkingSlotType(), true); // update board
        displayBoard.showDisplay();
    }

    private ParkingSlotType getParkingSlotType(VehicleCategory vehicleCategory) {
        switch (vehicleCategory) {
            case TwoWheeler: return ParkingSlotType.TwoWheeler;
            case Hatchback: return ParkingSlotType.Compact;
            case SUV: return ParkingSlotType.Medium;
            case Sedan: return ParkingSlotType.Medium;
            case Bus: return ParkingSlotType.Large;
        }
        return null;
    }
}
