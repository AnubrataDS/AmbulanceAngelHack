package anubrata.com.ambulanceangelhack.ViewHolder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import anubrata.com.ambulanceangelhack.Common.Common;
import anubrata.com.ambulanceangelhack.Model.Booking;
import anubrata.com.ambulanceangelhack.Model.Driver;
import anubrata.com.ambulanceangelhack.R;

public class PreInRideActivity extends AppCompatActivity
        implements
        OnMapReadyCallback {

    //variables
    private final float zoomLevel = 15.5f;
    private boolean isCritical = false;

    //UI declarations
    private Button normalButton;
    private Button criticalButton;

    //Maps declarations
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private LatLng userCurrentLocation;

    //Firebase Database declarations
    private FirebaseDatabase database;
    private DatabaseReference bookingRef,driverRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_in_ride);

        //removing title bar
        getSupportActionBar().hide();

        //by default normal selected
        normalButton = findViewById(R.id.normalButton);
        normalButton.setBackgroundResource(R.drawable.normaldark);

        //firebase initializations
        database = FirebaseDatabase.getInstance();
        //we have got the driverid from spring server
        //for now working with default driver "driver1"
        driverRef = database.getReference("drivers").child("driver1");

        //retreiving current location of user from previous activity
        Intent fromHomeActivity = getIntent();
        userCurrentLocation = new LatLng(Double.parseDouble(fromHomeActivity.getStringExtra("lat")) ,
                Double.parseDouble(fromHomeActivity.getStringExtra("lng")) );

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //buttons' respective onClickListeners
        normalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCritical = false;
                normalButton.setBackgroundResource(R.drawable.normaldark);
                criticalButton.setBackgroundResource(R.drawable.critical);
            }
        });

        criticalButton = findViewById(R.id.criticalButton);
        criticalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCritical = true;
                normalButton.setBackgroundResource(R.drawable.normal);
                criticalButton.setBackgroundResource(R.drawable.criticaldark);
            }
        });

        Button confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("drivers/1");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        Common.currentDriver = dataSnapshot.getValue(Driver.class);
                        Log.d("PreInRide", "DriverID is: " + Common.currentDriver.getDriverID());
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("PreInRide", "Failed to read value.", error.toException());
                    }
                });
                if(Common.currentDriver != null) {
                    Date currentTime = Calendar.getInstance().getTime();
                    String bookingID = Common.currentUser.getUserID() + currentTime.toString();
                    Common.currentUser.setCurrentBookingID(bookingID);
                    Common.currentDriver.setCurrentBookingID(bookingID);
                    bookingRef = database.getReference("bookings").child(bookingID);

                    //in future we can retrieve the driver details from spring server
                    //for now taking the only driver we have i.e. driver1
                    if (!isCritical)
                        Common.currentBooking = new Booking(bookingID, Common.currentUser.getUserID(), "driver1", "normal",
                                "Cowrks", "Fortis Hospital", "XXX"
                                , "XXX", "XXXXX", "true");
                    else
                        Common.currentBooking = new Booking(bookingID, Common.currentUser.getUserID(), "driver1", "critical",
                                "Cowrks", "Fortis Hospital", "XXX"
                                , "XXX", "XXXXX", "true");
                    bookingRef.setValue(Common.currentBooking);
                    driverRef.setValue(Common.currentDriver);

                    Intent toInRideActivity = new Intent(getApplicationContext(), InRideActivity.class);
                    startActivity(toInRideActivity);
                    finish();
                }
                else
                {
                    Toast.makeText(PreInRideActivity.this, "Assigning driver...", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //current location of patient
        googleMap.addMarker(new MarkerOptions().position(userCurrentLocation)
                .title("My Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userCurrentLocation, zoomLevel));
    }
}
