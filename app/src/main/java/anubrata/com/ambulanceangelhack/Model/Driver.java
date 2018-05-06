package anubrata.com.ambulanceangelhack.Model;

/**
 * Created by ADS on 5/5/2018.
 */

public class Driver {
    private String name, driverID, carDetails, assistantID, isAvailable,currentBookingID;
    private double longitude,latitude;

    public Driver() {    }

    public Driver(String name, String driverID, String carDetails, String assistantID,
                  String isAvailable, String currentBookingID,
                  double longitude, double latitude) {
        this.name = name;
        this.driverID = driverID;
        this.carDetails = carDetails;
        this.assistantID = assistantID;
        this.isAvailable = isAvailable;
        this.currentBookingID = currentBookingID;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getCurrentBookingID() {
        return currentBookingID;
    }

    public void setCurrentBookingID(String currentBookingID) {
        this.currentBookingID = currentBookingID;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getCarDetails() {
        return carDetails;
    }

    public void setCarDetails(String carDetails) {
        this.carDetails = carDetails;
    }

    public String getAssistantID() {
        return assistantID;
    }

    public void setAssistantID(String assistantID) {
        this.assistantID = assistantID;
    }
}
