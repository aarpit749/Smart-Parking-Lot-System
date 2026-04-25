import Entity.ParkingFloor;
import Entity.ParkingSpot;
import Entity.ParkingTicket;
import Entity.Vehicle;
import Service.ParkingLotService;
import Enum.VehicleType;
import Enum.SpotType;
import java.util.*;


public class Main {

    public static void main(String[] args) {

        List<ParkingFloor> floors = createParkingFloors(5);

        ParkingLotService parkingLotService = new ParkingLotService(floors);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== SMART PARKING LOT SYSTEM =====");
            System.out.println("1. Vehicle Entry (Check‑In)");
            System.out.println("2. Vehicle Exit (Check‑Out)");
            System.out.println("3. View Available Parking Spots (EXTRA)");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {

                case 1:
                    System.out.print("Enter vehicle number: ");
                    String number = scanner.nextLine();

                    System.out.print("Enter vehicle type (MOTORCYCLE / CAR / BUS): ");
                    VehicleType type =
                            VehicleType.valueOf(scanner.nextLine().toUpperCase());

                    try {
                        Vehicle vehicle = new Vehicle(number, type);
                        ParkingTicket ticket =
                                parkingLotService.checkIn(vehicle);

                        System.out.println("✅ Vehicle parked successfully");
                        System.out.println("Ticket ID: " + ticket.getTicketId());
                        System.out.println("Allocated Spot: " +
                                ticket.getSpot().getId());

                    } catch (Exception e) {
                        System.out.println("❌ " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.print("Enter vehicle number: ");
                    String exitNumber = scanner.nextLine();

                    try {
                        double fee =
                                parkingLotService.checkOut(exitNumber);
                        System.out.println("✅ Vehicle exited");
                        System.out.println("Parking Fee: ₹" + fee);

                    } catch (Exception e) {
                        System.out.println("❌ " + e.getMessage());
                    }
                    break;
                case 3:
                    parkingLotService.showAvailableSpots();
                    break;

                case 4:
                    System.out.println("Exiting system...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("❌ Invalid choice");
            }
        }
    }

    private static List<ParkingFloor> createParkingFloors(int numberOfFloors) {

        List<ParkingFloor> floors = new ArrayList<>();

        for (int floorNum = 1; floorNum <= numberOfFloors; floorNum++) {

            List<ParkingSpot> spots = new ArrayList<>();
            int spotCounter = 1;

            // 3 SMALL spots
            for (int i = 0; i < 3; i++) {
                spots.add(new ParkingSpot(
                        "F" + floorNum + "-S" + spotCounter++, SpotType.SMALL));
            }

            // 3 MEDIUM spots
            for (int i = 0; i < 3; i++) {
                spots.add(new ParkingSpot(
                        "F" + floorNum + "-S" + spotCounter++, SpotType.MEDIUM));
            }

            // 3 LARGE spots
            for (int i = 0; i < 3; i++) {
                spots.add(new ParkingSpot(
                        "F" + floorNum + "-S" + spotCounter++, SpotType.LARGE));
            }

            floors.add(new ParkingFloor(floorNum, spots));
        }

        return floors;
    }
}
