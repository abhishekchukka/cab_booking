import java.util.*;

public class CabManager {
    private static final int MAX_CABS_DIST = 10;
    private Map<String, Driver> driverDetails;
    private Map<String, String> cabStatus;
    private Map<String, Location> driverLocations;
    private Map<String, RideDetails> activeRides;
    DriverAssignmentStrategy strategy;

    public CabManager(
            DriverAssignmentStrategy strategy) {
        this.driverDetails = new HashMap<>();
        this.cabStatus = new HashMap<>();
        this.driverLocations = new HashMap<>();
        this.activeRides = new HashMap<>();
        this.strategy = strategy;
    }

    public Map<String, Driver> getDriverDetails() {
        return driverDetails;
    }

    public Map<String, String> getCabStatus() {
        return cabStatus;
    }
    
    public void addOrUpdateCabLocation(String id, Location location) {
        driverLocations.put(id, location);
    }

    public Map<String, Location> getDriverLocations() {
        return driverLocations;
    }

    public Map<String, RideDetails> getActiveRides() {
        return activeRides;
    }

    public void registerDriver(Driver d) {
        driverDetails.put(d.getID(), d);
    }

    public void updateCabStatus(String id, String status) {
        cabStatus.put(id, status);
    }

    public Driver getAvailableCabs(Location userLocation) {
        List<Driver> availableDrivers = new ArrayList<>();
        for (Map.Entry<String, Location> entry : driverLocations.entrySet()) {
            String driverId = entry.getKey();
            Location driverLocation = entry.getValue();

            if (userLocation.calculateDistance(driverLocation) < MAX_CABS_DIST &&
                    cabStatus.get(driverId).equals("free")) {
                availableDrivers.add(driverDetails.get(driverId));
            }
        }
        System.out.println(availableDrivers);
        return strategy.assignDriver(availableDrivers,
                userLocation, getDriverLocations());

    }

}
