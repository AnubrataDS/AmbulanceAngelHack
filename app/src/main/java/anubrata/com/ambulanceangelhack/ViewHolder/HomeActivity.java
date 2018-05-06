package anubrata.com.ambulanceangelhack.ViewHolder;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import anubrata.com.ambulanceangelhack.Common.Common;
import anubrata.com.ambulanceangelhack.Model.User;
import anubrata.com.ambulanceangelhack.R;

public class HomeActivity extends AppCompatActivity implements
        OnMapReadyCallback  {

    //variables
    private final float zoomLevel = 15.5f;

    //Maps declarations
    private SupportMapFragment mapFragment;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);




        //removing the actionbar
        //getActionBar().hide();
        getSupportActionBar().hide();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //getting user object
        Common.currentUser = new User("sam1234" , "Samuel" , null);

        Button bookButton = findViewById(R.id.bookButton);
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if currentBookingID is null this means that no active rides are there
                //else active ride is there
                if(Common.currentUser.getCurrentBookingID() == null) {
                    Intent toPreInRideActivity = new Intent(getApplicationContext(), PreInRideActivity.class);
                    toPreInRideActivity.putExtra("lat", "28.490220");
                    toPreInRideActivity.putExtra("lng", "77.078901");
                    startActivity(toPreInRideActivity);
                }
                else
                {
                    Toast.makeText(HomeActivity.this, "Ongoing ride.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button sosButton = findViewById(R.id.sosButton);
        sosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if currentBookingID is null this means that no active rides are there
                //else active ride is there
                if(Common.currentUser.getCurrentBookingID() == null) {
                    Intent i = new Intent(HomeActivity.this, Popup.class);
                    startActivity(i);//dialog box ,\so on and so forth
                }
                else
                {
                    Toast.makeText(HomeActivity.this, "Ongoing ride.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        //current location of patient
        LatLng patientLocation = new LatLng(28.490220, 77.078901);
        googleMap.addMarker(new MarkerOptions().position(patientLocation)
                .title("My Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(patientLocation, zoomLevel));
    }
}
