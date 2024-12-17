
public class BookingManager {
    private CabManager cabManager;
    private PriceCalculator calc;
    private Location destination;

    public BookingManager(CabManager cabManager, PriceCalculator calc) {
        this.cabManager = cabManager;
        this.calc = calc;
    }

    public RideDetails bookRide(Rider rider, Driver driver, Location riderLocation, Location Destination) {
        this.destination = Destination;
        double distance = riderLocation.calculateDistance(Destination);
        double amount = calc.calculatePrice(distance);
        RideDetails rd = new RideDetails(rider, driver, distance, amount);
        // if (cabManager.getActiveRides().size() == ) {
        // System.out.println("The Drivers are busy");
        // return null;
        // }

        cabManager.getActiveRides().put(rider.getID(), rd);
        cabManager.updateCabStatus(driver.getID(), "onRide");
        System.out.println("Ride Began ->" + rd.getRideDetails());
        return rd;
    }

    public void endRide(RideDetails rideDetails, RiderManager rm) {

        Rider rider = rideDetails.getRider();
        Driver driver = rideDetails.getDriver();

        String driverId = driver.getID();

        if (cabManager.getActiveRides().containsKey(rider.getID())) {

            rm.addRideToHistory(rider, rideDetails);

            cabManager.updateCabStatus(driverId, "free");
            cabManager.addOrUpdateCabLocation(driverId, destination);
            cabManager.getActiveRides().remove(rider.getID());

            System.out.println("Ride ended: " + rideDetails);
        } else {
            System.out.println("No active ride found for Rider: " + rider.getName());
        }
    }

}
