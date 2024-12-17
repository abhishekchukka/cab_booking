import java.util.*;

public class RiderTester {

    // Setup method to add drivers to the system
    public static void setupDrivers(CabManager cabManager) {
        Car car1 = new Car("Maruti", 123);
        Car car2 = new Car("Audi", 456);
        Car car3 = new Car("Toyota", 789);

        Driver driver1 = new Driver();
        Driver driver2 = new Driver();
        Driver driver3 = new Driver();

        driver1.registerDriverWithCar("Driver1", 123123, car1);
        driver2.registerDriverWithCar("Driver2", 456456, car2);
        driver3.registerDriverWithCar("Driver3", 789789, car3);

        cabManager.registerDriver(driver1);
        cabManager.registerDriver(driver2);
        cabManager.registerDriver(driver3);

        cabManager.addOrUpdateCabLocation(driver1.getID(), new Location(10, 10));
        cabManager.addOrUpdateCabLocation(driver2.getID(), new Location(12, 12));
        cabManager.addOrUpdateCabLocation(driver3.getID(), new Location(15, 15));

        cabManager.updateCabStatus(driver1.getID(), "free");
        cabManager.updateCabStatus(driver2.getID(), "free");
        cabManager.updateCabStatus(driver3.getID(), "free");

        System.out.println("Drivers initialized.\n");
    }

    // Driver Menu handling logic
    private static void driverMenu(Scanner scanner, CabManager cabManager) {
        while (true) {
            System.out.println("\nDriver Menu:");
            System.out.println("1. Add Driver");
            System.out.println("2. Delete Driver");
            
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addDriver(scanner, cabManager);
                    
                    break;
                case 2:
                    deleteDriver(scanner, cabManager);
                    break;
                case 3:
                    return; // Back to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Add Driver Method
    private static void addDriver(Scanner scanner, CabManager cabManager) {
        System.out.print("Enter Driver Name: ");
        String driverName = scanner.nextLine();
        System.out.print("Enter License Number: ");
        int licenseNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Car Name: ");
        String carName = scanner.nextLine();
        System.out.print("Enter Car Number: ");
        int carNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Driver driver = new Driver();
        driver.registerDriverWithCar(driverName, licenseNumber, new Car(carName, carNumber));
        cabManager.registerDriver(driver);
        System.out.print("Enter Driver's initial X coordinate: ");
        int x = scanner.nextInt();
        System.out.print("Enter Driver's initial Y coordinate: ");
        int y = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        cabManager.addOrUpdateCabLocation(driver.getID(), new Location(x, y));
        cabManager.updateCabStatus(driver.getID(), "free");
        
        System.out.println("Driver added successfully: " + driverName);
        System.out.println("Initial location and status set for Driver: " + driverName);
    }

    // Delete Driver Method
    private static void deleteDriver(Scanner scanner, CabManager cabManager) {
        System.out.print("Enter the Driver Name to delete: ");
        String driverName = scanner.nextLine();
        Driver driverToRemove = null;

        for (Driver driver : cabManager.getDriverDetails().values()) {
            if (driver.getName().equals(driverName)) {
                driverToRemove = driver;
                break;
            }
        }

        if (driverToRemove != null) {
            cabManager.getDriverDetails().remove(driverToRemove.getID());
            cabManager.getDriverLocations().remove(driverToRemove.getID());
            cabManager.getCabStatus().remove(driverToRemove.getID());
            System.out.println("Driver deleted successfully: " + driverName);
        } else {
            System.out.println("Driver not found: " + driverName);
        }
    }

    // Set Initial Location and Status for Driver
    private static void setInitialLocationAndStatus(Scanner scanner, CabManager cabManager) {
        System.out.print("Enter Driver Name: ");
        String driverName = scanner.nextLine();

        Driver driver = null;
        for (Driver d : cabManager.getDriverDetails().values()) {
            if (d.getName().equals(driverName)) {
                driver = d;
                break;
            }
        }

        if (driver == null) {
            System.out.println("Driver not found: " + driverName);
            return;
        }

        
    }

    // Rider Menu handling logic
    private static void riderMenu(Scanner scanner, CabManager cabManager, RiderManager riderManager, BookingManager bookingManager) {
        while (true) {
            System.out.println("\nRider Menu:");
            System.out.println("1. Add Rider and Book Ride");
            System.out.println("2. End a Ride");
            System.out.println("3. View Ride History");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addRiderAndStartRide(scanner, cabManager, riderManager, bookingManager);
                    break;
                case 2:
                    endRide(scanner, riderManager);
                    break;
                case 3:
                    viewRideHistory(scanner, riderManager);
                    break;
                case 4:
                    return; // Back to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Add Rider and Start Ride
    private static void addRiderAndStartRide(Scanner scanner, CabManager cabManager, RiderManager riderManager, BookingManager bookingManager) {
        System.out.print("Enter Rider Name: ");
        String riderName = scanner.nextLine();
    
        Rider rider = new Rider(riderName);
        riderManager.setRiderDetailsForRide(rider);
        System.out.println("Rider added successfully: " + riderName);
    
        System.out.print("Enter Rider's Current X Location: ");
        int x = scanner.nextInt();
        System.out.print("Enter Rider's Current Y Location: ");
        int y = scanner.nextInt();
        scanner.nextLine(); // Consume newline
    
        riderManager.setOrUpdateRiderLocation(rider.getID(), new Location(x, y));
    
        // Get the rider's location
        Location riderLocation = riderManager.getRiderLocation(rider.getID());
        if (riderLocation == null) {
            System.out.println("Rider location is not set.");
            return;
        }
    
        Driver selectedDriver = cabManager.getAvailableCabs(riderLocation);
        if (selectedDriver != null) {
            System.out.println("Selected Driver: " + selectedDriver.getName());
    
            System.out.print("Enter Destination X Location: ");
            int destX = scanner.nextInt();
            System.out.print("Enter Destination Y Location: ");
            int destY = scanner.nextInt();
            scanner.nextLine(); // Consume newline
    
            Location destinationLocation = new Location(destX, destY);
    
            // Now attempt to book the ride
            RideDetails ride = bookingManager.bookRide(rider, selectedDriver, riderLocation, destinationLocation);
            if (ride != null) {
                System.out.println("Ride booked successfully!");
            } else {
                System.out.println("Failed to book ride.");
            }
        } else {
            System.out.println("No drivers available for Rider: " + rider.getName());
        }
    }
    

    // End Ride Method
    private static void endRide(Scanner scanner, RiderManager riderManager) {
        System.out.print("Enter Rider Name to End the Ride: ");
        String riderName = scanner.nextLine();

        System.out.println("Ending ride for: " + riderName);
        riderManager.endRideForRider(riderName);
    }

    // View Ride History
    private static void viewRideHistory(Scanner scanner, RiderManager riderManager) {
        System.out.print("Enter Rider Name to View Ride History: ");
        String riderName = scanner.nextLine();

        Rider rider = riderManager.getRiderByName(riderName);
        if (rider != null) {
            List<RideDetails> history = riderManager.getRiderHistory(rider.getID());
            if (history != null && !history.isEmpty()) {
                System.out.println("Ride History for " + riderName + ":");
                for (RideDetails ride : history) {
                    System.out.println(ride);
                }
            } else {
                System.out.println("No ride history found for " + riderName);
            }
        } else {
            System.out.println("Rider not found.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Managers
        CabManager cabManager = new CabManager(new DefaultAssignmentStrategy());
        RiderManager riderManager = new RiderManager();
        BookingManager bookingManager = new BookingManager(cabManager, new BasicPriceCalculator());

        // Setup drivers
        setupDrivers(cabManager);

        while (true) {
            System.out.println("\nChoose an interface:");
            System.out.println("1. Driver Interface");
            System.out.println("2. Rider Interface");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    driverMenu(scanner, cabManager);
                    break;
                case 2:
                    riderMenu(scanner, cabManager, riderManager, bookingManager);
                    break;
                case 3:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    return; // Exit the program
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
