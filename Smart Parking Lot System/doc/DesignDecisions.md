📘 Design Decisions – Smart Parking Lot System
This system is designed using object‑oriented principles with a clear separation of responsibilities. The goal is to build a scalable, maintainable backend for a smart parking lot with multiple floors and support for different vehicle types.

1. Overall Architectural Style
   Monolithic, Layered Architecture (LLD‑focused)

The system is designed as a single backend application, suitable for low‑level design.
The focus is on domain modeling and business logic, not frameworks or deployment.

Layers:

Presentation Layer

Main
Handles user input/output (console-based menu).


Service Layer

ParkingLotService
SpotAllocationService
Contains core business logic.


Domain / Entity Layer

Vehicle, ParkingSpot, ParkingFloor, ParkingTicket


Utility / Strategy Layer

FeeCalculator


Enum Layer

VehicleType, SpotType, SpotStatus




2. Entity Design Decisions
   2.1 Vehicle
   Responsibility: Represents a vehicle entering the parking lot.
   Key Decisions:

VehicleType is an enum to avoid invalid vehicle categories.
Vehicle itself does not store parking duration or fee, following SRP.

✅ Keeps vehicle model lightweight and reusable.

2.2 ParkingSpot
Responsibility: Represents a physical parking space.
Key Decisions:

Each spot has:

spotType (SMALL / MEDIUM / LARGE)
status (AVAILABLE / OCCUPIED)


assign() and release() methods are synchronized.

✅ Ensures thread‑safe spot allocation.
✅ Spot manages its own availability state.

2.3 ParkingFloor
Responsibility: Logical grouping of parking spots.
Key Decisions:

Floor contains only:

floorNumber
List<ParkingSpot>


No allocation or fee logic inside ParkingFloor.

✅ Floors act as structural containers, not logic-heavy classes.
✅ Reflects real‑world parking lot layout.

2.4 ParkingTicket
Responsibility: Represents a single parking transaction.
Key Decisions:

Stores:

Vehicle
Allocated parking spot
Entry time
Exit time
Calculated fee


Entry time is final (immutable after check‑in).
Exit time is set only at check‑out.

✅ Correct modeling of a real parking transaction.
✅ Entry and exit times are recorded as required by the problem statement.

3. Service Layer Design Decisions
   3.1 ParkingLotService
   Responsibility: Orchestrates the parking workflow.
   Handles:

Vehicle check‑in
Vehicle check‑out
Ticket lifecycle
Spot release
(EXTRA) Availability view

Key Decisions:

Maintains activeTickets as a Map<licenseNumber, ParkingTicket>
Check‑in and check‑out methods are synchronized

✅ Protects against concurrent entry/exit issues.
✅ Central authority for parking operations.

3.2 SpotAllocationService
Responsibility: Allocates a parking spot based on vehicle type.
Key Decisions:

Allocation algorithm:

Iterate floors sequentially
Iterate spots within each floor
Apply vehicle‑to‑spot compatibility rules


First‑fit strategy for simplicity and efficiency.

✅ Clean separation of allocation logic.
✅ Easy to optimize or replace later (e.g., nearest‑spot strategy).

4. Fee Calculation Strategy
   4.1 FeeCalculator
   Responsibility: Calculates parking fee.
   Key Decisions:

Fee is calculated at exit time.
Based on:

Parking duration (hours)
Vehicle type


Minimum charge of 1 hour is enforced.

✅ Pricing logic is isolated and extensible.
✅ Can be enhanced for:

Weekend pricing
Night charges
Flat fees


5. Enums Usage
   Enums are used to enforce system constraints at compile time.





















EnumPurposeVehicleTypeRestricts vehicle categoriesSpotTypeRestricts parking spot sizesSpotStatusControls availability state
✅ Avoids magic strings and invalid values.
✅ Improves readability and safety.

6. Application Setup (Main Class)
   Key Decisions:

Main is responsible only for:

System initialization
Creating floors and spots using a helper method
Menu‑driven user interaction


Helper method generates 5 floors, each with:

3 SMALL
3 MEDIUM
3 LARGE spots



✅ Setup logic is separated from business logic.
✅ Avoids duplication and supports scalability.

7. Concurrency Handling

ParkingLotService.checkIn() → synchronized
ParkingLotService.checkOut() → synchronized
ParkingSpot.assign() / release() → synchronized

✅ Ensures:

No two vehicles get the same spot
Consistent system state under concurrent usage