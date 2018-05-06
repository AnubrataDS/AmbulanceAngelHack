package anubrata.com.ambulanceangelhack.Model;

/**
 * Created by ADS on 5/5/2018.
 */

public class User {
    String userID, name, currentBookingID;

    public User() {
    }

    public User(String userID, String name, String currentBookingID) {
        this.userID = userID;
        this.name = name;
        this.currentBookingID = currentBookingID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentBookingID() {
        return currentBookingID;
    }

    public void setCurrentBookingID(String currentBookingID) {
        this.currentBookingID = currentBookingID;
    }
}
