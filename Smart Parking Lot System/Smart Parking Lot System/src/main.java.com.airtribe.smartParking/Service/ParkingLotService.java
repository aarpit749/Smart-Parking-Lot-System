package Service;

import Entity.ParkingFloor;
import Entity.ParkingSpot;
import Entity.ParkingTicket;
import Entity.Vehicle;
import Stategy.FeeCalculator;
import Enum.SpotStatus;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ParkingLotService {

    private final List<ParkingFloor> floors;
    private final SpotAllocationService allocator;
    private final FeeCalculator feeCalculator;
    private final Map<String, ParkingTicket> activeTickets = new HashMap<>();

    public ParkingLotService(List<ParkingFloor> floors) {
        this.floors = floors;
        this.allocator = new SpotAllocationService();
        this.feeCalculator = new FeeCalculator();
    }

    public synchronized ParkingTicket checkIn(Vehicle vehicle) {
        ParkingSpot spot = allocator.allocateSpot(
                vehicle.getVehicleType(), floors);

        if (spot == null) {
            throw new RuntimeException("No parking spots available");
        }

        String ticketId = UUID.randomUUID().toString();
        ParkingTicket ticket = new ParkingTicket(
                ticketId, vehicle, spot, LocalDateTime.now());

        activeTickets.put(vehicle.getLicenseNumber(), ticket);
        return ticket;
    }

    public synchronized double checkOut(String licenseNumber) {
        ParkingTicket ticket = activeTickets.get(licenseNumber);

        if (ticket == null) {
            throw new RuntimeException("Invalid ticket");
        }

        LocalDateTime exitTime = LocalDateTime.now();
        double fee = feeCalculator.calculateFee(
                ticket.getVehicle().getVehicleType(),
                ticket.getEntryTime(),
                exitTime);

        ticket.closeTicket(exitTime, fee);
        ticket.getSpot().release();
        activeTickets.remove(licenseNumber);
        System.out.println("Entry Time : " + ticket.getEntryTime());
        System.out.println("Exit Time  : " + exitTime);

        return fee;
    }


    public void showAvailableSpots() {
        System.out.println("\n--- Available Parking Spots (EXTRA) ---");

        for (ParkingFloor floor : floors) {
            int small = 0, medium = 0, large = 0;

            for (ParkingSpot spot : floor.getSpots()) {
                if (spot.getStatus() == SpotStatus.AVAILABLE) {
                    switch (spot.getSpotType()) {
                        case SMALL:
                            small++;
                            break;
                        case MEDIUM:
                            medium++;
                            break;
                        case LARGE:
                            large++;
                            break;
                    }
                }
            }

            System.out.println("Floor " + floor.getFloorNumber() + ":");
            System.out.println("  SMALL  : " + small);
            System.out.println("  MEDIUM : " + medium);
            System.out.println("  LARGE  : " + large);
        }
    }

}
