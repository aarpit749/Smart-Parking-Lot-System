package Entity;

import java.time.LocalDateTime;

public class ParkingTicket {

    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSpot spot;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double fee;

    public ParkingTicket(String ticketId, Vehicle vehicle,
                         ParkingSpot spot, LocalDateTime entryTime) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.spot = spot;
        this.entryTime = entryTime;
    }

    public void closeTicket(LocalDateTime exitTime, double fee) {
        this.exitTime = exitTime;
        this.fee = fee;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSpot getSpot() {
        return spot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public String getTicketId() {
        return ticketId;
    }

    public double getFee() {
        return fee;
    }

}
