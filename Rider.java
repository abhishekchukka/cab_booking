import java.util.UUID;

public class Rider {
    private String name;
    private String id;
    //private static int idGenerator = 1;
    private Location location;
    private RideDetails currentRide;
    // private List<String> rideHistory; // Simple ride history

    public Rider(String name) {
        this.id = UUID.randomUUID().toString(); // Generate unique ID
        this.name = name;
        this.currentRide = null;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return id;
    }
    public RideDetails getCurrentRide() {
        return currentRide;
    }
    public void setCurrentRide(RideDetails currentRide) {
        this.currentRide = currentRide;
    }
    public Location getLocation() {
        return location;
    }

    // Setter for Rider's location
    public void setLocation(Location location) {
        this.location = location;
    }
    public void endRide() {
        // Assuming the ride is finished, we nullify the currentRide
        this.currentRide = null;
    }
}
