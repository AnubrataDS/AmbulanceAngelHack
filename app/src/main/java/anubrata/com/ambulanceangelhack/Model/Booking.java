package anubrata.com.ambulanceangelhack.Model;

/**
 * Created by ADS on 5/5/2018.
 */

public class Booking {
    String bookingID, userID, driverID,type,
            source, destination,
            distance, journeyTime, amount, isActive;

    public Booking() {    }

    public Booking(String bookingID, String userID, String driverID, String type,
                   String source, String destination,
                   String distance, String journeyTime, String amount, String isActive) {
        this.bookingID = bookingID;
        this.userID = userID;
        this.driverID = driverID;
        this.type = type;
        this.source = source;
        this.destination = destination;
        this.distance = distance;
        this.journeyTime = journeyTime;
        this.amount = amount;
        this.isActive = isActive;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getJourneyTime() {
        return journeyTime;
    }

    public void setJourneyTime(String journeyTime) {
        this.journeyTime = journeyTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
