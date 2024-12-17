import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class RiderManager {
    private Map<String, Rider> riders = new HashMap<>();
    private Map<String, List<RideDetails>> rideHistory = new HashMap<>();

    // Add a rider to the system
    public void setRiderDetailsForRide(Rider rider) {
        riders.put(rider.getID(), rider);
    }

    // Get rider by name
    public Rider getRiderByName(String riderName) {
        for (Rider rider : riders.values()) {
            if (rider.getName().equals(riderName)) {
                return rider;
            }
        }
        return null; // If no rider found with the name
    }

    // Update or set rider location
    public void setOrUpdateRiderLocation(String riderID, Location location) {
        Rider rider = riders.get(riderID);
        if (rider != null) {
            rider.setLocation(location);
        }
    }

    // Get rider location
    public Location getRiderLocation(String riderID) {
        Rider rider = riders.get(riderID);
        return rider != null ? rider.getLocation() : null;
    }
    public void endRideForRider(String riderName) {
        Rider rider = riders.get(riderName);
        if (rider != null) {
            RideDetails rideDetails = rider.getCurrentRide();
            if (rideDetails != null) {
                // End the ride in the rider's object
                rider.endRide();
                // Optionally, you can also log this into the ride history or perform other operations.
                System.out.println("Ride ended for rider " + riderName);
            } else {
                System.out.println("No active ride found for rider " + riderName);
            }
        } else {
            System.out.println("Rider not found.");
        }
    }

    // Add a ride to a rider's history
    public void addRideToHistory(Rider rider, RideDetails rideDetails) {
        // Get the rider's history or create a new list if none exists
        List<RideDetails> history = rideHistory.getOrDefault(rider.getID(), new ArrayList<>());
        history.add(rideDetails);
        rideHistory.put(rider.getID(), history);
    }

    // Get ride history of a rider
    public List<RideDetails> getRiderHistory(String riderID) {
        return rideHistory.getOrDefault(riderID, new ArrayList<>());
    }

}
