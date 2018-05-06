package anubrata.com.ambulanceangelhack.ViewHolder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import anubrata.com.ambulanceangelhack.Common.Common;
import anubrata.com.ambulanceangelhack.R;

public class InvoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        //removing title bar
        getSupportActionBar().hide();

        TextView fareTextView = findViewById(R.id.fareTextView);
        fareTextView.setText("₹" + Common.currentBooking.getAmount());

        TextView driverTextView = findViewById(R.id.drivernameTextView);
        driverTextView.setText("Your ride with Mr." + Common.currentDriver.getName());

        TextView destTextView = findViewById(R.id.destinationTextView);
        destTextView.setText("To : " + Common.currentBooking.getDestination());


        //the date and time can be extracted from bookingID itself


        //since the ride has ended , now the app will be made free for next ride if possible
        Common.currentDriver = null;
        Common.currentBooking = null;
        Common.currentUser.setCurrentBookingID(null);



    }
}
