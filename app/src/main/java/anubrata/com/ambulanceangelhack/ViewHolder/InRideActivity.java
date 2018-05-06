package anubrata.com.ambulanceangelhack.ViewHolder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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

public class InRideActivity extends AppCompatActivity implements
        OnMapReadyCallback {


    //variables
    private final float zoomLevel = 15.5f;
    private final String TAG = "InRideActivity";

    //Maps declarations
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private LatLng patientLocation;
    private MarkerOptions marker ;
    //Firebase Database declarations
    DatabaseReference driverRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_ride);

        //firebase database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //assuming that driver object is initiated
        driverRef = database.getReference("drivers")
                .child(Common.currentDriver.getDriverID());
        Log.d("ref" ,driverRef.toString());

        //removing title bar
        getSupportActionBar().hide();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // Read from the database
        driverRef.child("longitude").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                double value = dataSnapshot.getValue(Double.class);
                if (Common.currentDriver  != null) {
                    Common.currentDriver.setLongitude(value);
                    Log.d(TAG, "Longitude is: " + value);
                    driverRef.child("latitude").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            double value = dataSnapshot.getValue(Double.class);
                            if (Common.currentDriver != null) {
                                Common.currentDriver.setLatitude(value);
                                Log.d(TAG, "Latitude is: " + value);
                                mMap.clear();
                                patientLocation = new LatLng(Common.currentDriver.getLatitude(),
                                        Common.currentDriver.getLongitude());
                                marker = new MarkerOptions().position(patientLocation)
                                        .title("My Location")
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ambulance_small));
                                mMap.addMarker(marker);
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(patientLocation, zoomLevel));
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Failed to read value
                            Log.w(TAG, "Failed to read value.", databaseError.toException());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        DatabaseReference bookingRef = database.getReference("bookings").child(Common.currentBooking.getBookingID());
        bookingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Booking finalBooking = dataSnapshot.getValue(Booking.class);
                if(finalBooking.getIsActive().equals("false"))
                {
                    Intent toInvoiceActivity = new Intent(getApplicationContext() , InvoiceActivity.class);
                    startActivity(toInvoiceActivity);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        //current location of patient
        mMap = googleMap;
        patientLocation = new LatLng(28.490220, 77.078901);
        marker = new MarkerOptions().position(patientLocation)
                .title("My Location");
        googleMap.addMarker(marker).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ambulance_small));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(patientLocation, zoomLevel));
    }
}
