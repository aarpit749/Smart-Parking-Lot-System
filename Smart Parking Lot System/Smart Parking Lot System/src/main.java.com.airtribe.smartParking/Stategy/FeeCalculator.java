package Stategy;

import java.time.Duration;
import java.time.LocalDateTime;
import Enum.VehicleType;

public class FeeCalculator {

    public double calculateFee(VehicleType type,
                               LocalDateTime entry,
                               LocalDateTime exit) {

        long hours = Math.max(1, Duration.between(entry, exit).toHours());

        switch (type) {
            case MOTORCYCLE:
                return hours * 10;
            case CAR:
                return hours * 20;
            case BUS:
                return hours * 50;
            default:
                throw new IllegalArgumentException("Invalid vehicle type");
        }
    }

}
