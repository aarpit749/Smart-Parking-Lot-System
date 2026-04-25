package Entity;

import Enum.SpotStatus;
import Enum.SpotType;

public class ParkingSpot {

    private final String id;
    private final SpotType spotType;
    private SpotStatus status;

    public ParkingSpot(String id, SpotType spotType) {
        this.id = id;
        this.spotType = spotType;
        this.status = SpotStatus.AVAILABLE;
    }

    public synchronized boolean assign() {
        if (status == SpotStatus.AVAILABLE) {
            status = SpotStatus.OCCUPIED;
            return true;
        }
        return false;
    }

    public synchronized void release() {
        status = SpotStatus.AVAILABLE;
    }

    public SpotType getSpotType() {
        return spotType;
    }

    public SpotStatus getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }
}

