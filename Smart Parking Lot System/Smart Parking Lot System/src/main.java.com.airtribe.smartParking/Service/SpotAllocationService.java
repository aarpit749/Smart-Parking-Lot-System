package Service;

import Entity.ParkingFloor;
import Entity.ParkingSpot;
import Enum.VehicleType;
import Enum.SpotType;
import java.util.EnumSet;
import java.util.List;

public class SpotAllocationService {

    public ParkingSpot allocateSpot(VehicleType vehicleType,
                                    List<ParkingFloor> floors) {

        EnumSet<SpotType> allowedSpots = getAllowedSpots(vehicleType);

        for (ParkingFloor floor : floors) {
            for (ParkingSpot spot : floor.getSpots()) {
                if (allowedSpots.contains(spot.getSpotType())
                        && spot.assign()) {
                    return spot;
                }
            }
        }
        return null;
    }

    private EnumSet<SpotType> getAllowedSpots(VehicleType type) {
        switch (type) {
            case MOTORCYCLE:
                return EnumSet.of(SpotType.SMALL, SpotType.MEDIUM, SpotType.LARGE);
            case CAR:
                return EnumSet.of(SpotType.MEDIUM, SpotType.LARGE);
            case BUS:
                return EnumSet.of(SpotType.LARGE);
            default:
                throw new IllegalArgumentException();
        }
    }

}
