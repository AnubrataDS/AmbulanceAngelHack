package anubrata.com.ambulanceangelhack.ViewHolder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

import anubrata.com.ambulanceangelhack.Common.Common;
import anubrata.com.ambulanceangelhack.Model.Booking;
import anubrata.com.ambulanceangelhack.Model.Driver;
import anubrata.com.ambulanceangelhack.R;

public class FinalPopup extends AppCompatActivity {


    //Firebase Database declarations
    private FirebaseDatabase database;
    private DatabaseReference bookingRef,driverRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_popup);
        //removing title bar
        getSupportActionBar().hide();
        modifyUI();

        //firebase initialzing
        database = FirebaseDatabase.getInstance();


        DatabaseReference myRef = database.getReference("drivers/1");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Common.currentDriver = dataSnapshot.getValue(Driver.class);
                Log.d("FinalPopup", "DriverID is: " + Common.currentDriver.getDriverID());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("FinalPopup", "Failed to read value.", error.toException());
            }
        });


        Button selfButton = findViewById(R.id.yesButton);
        selfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Common.currentDriver != null) {
                    //normal high priority ride and payment taken from user
                    Date currentTime = Calendar.getInstance().getTime();
                    String bookingID = Common.currentUser.getUserID() + currentTime.toString();
                    Common.currentUser.setCurrentBookingID(bookingID);
                    Common.currentDriver.setCurrentBookingID(bookingID);
                    bookingRef = database.getReference("bookings").child(bookingID);
                    //we have got the driverid from spring server
                    //for now working with default driver "driver1"
                    driverRef = database.getReference("drivers").child("driver1");
                    Common.currentDriver.setCurrentBookingID(bookingID);
                    //in future we can retrieve the driver details from spring server
                    //for now taking the only driver we have i.e. driver1
                    Common.currentBooking = new Booking(bookingID, Common.currentUser.getUserID() , "driver1", "SOSSelf",
                            "Cowrks","Fortis Hospital", "XXX"
                            ,"XXX","XXXXX","true");

                    bookingRef.setValue(Common.currentBooking);
                    driverRef.setValue(Common.currentDriver);
                    Intent toInRideActivity = new Intent(FinalPopup.this, InRideActivity.class);
                    startActivity(toInRideActivity);
                    finish();
                }
                else
                {
                    Toast.makeText(FinalPopup.this, "Loading...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button someoneElseButton = findViewById(R.id.someoneElseButton);
        someoneElseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Common.currentDriver != null) {
                    // this means this is charity ride
                    //normal high priority ride and payment taken from user
                    Date currentTime = Calendar.getInstance().getTime();
                    String bookingID = Common.currentUser.getUserID() + currentTime.toString();
                    Common.currentUser.setCurrentBookingID(bookingID);
                    Common.currentDriver.setCurrentBookingID(bookingID);

                    bookingRef = database.getReference("bookings").child(bookingID);
                    //we have got the driverid from spring server
                    //for now working with default driver "driver1"
                    driverRef = database.getReference("drivers").child("driver1");
                    //in future we can retrieve the driver details from spring server
                    //for now taking the only driver we have i.e. driver1
                    Common.currentBooking = new Booking(bookingID, Common.currentUser.getUserID() , "driver1", "SOSSomeoneelse",
                            "Cowrks","Fortis Hospital", "XXX"
                            ,"XXX","XXXXX","true");

                    bookingRef.setValue(Common.currentBooking);
                    driverRef.setValue(Common.currentDriver);
                    Intent toInRideActivity = new Intent(FinalPopup.this, InRideActivity.class);
                    startActivity(toInRideActivity);
                    finish();
                }
                else {
                    Toast.makeText(FinalPopup.this, "Loading...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void modifyUI()
    {
        //making the popup appear infront of the previous activity
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.5),(int)(height*0.30));
    }
}
