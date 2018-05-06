package anubrata.com.ambulanceangelhack.ViewHolder;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import anubrata.com.ambulanceangelhack.R;

/**
 * Created by ADS on 5/5/2018.
 */

public class Popup extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        //removing title bar

        getSupportActionBar().hide();
        modifyUI();


        Button yesButton = (Button)findViewById(R.id.yesButton);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toFinalPopup  = new Intent(Popup.this,FinalPopup.class);
                startActivity(toFinalPopup);
                finish();
            }
        });

        Button noButton = (Button)findViewById(R.id.noButton);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toHomeActivity = new Intent(Popup.this,HomeActivity.class);
                startActivity(toHomeActivity);
                finish();
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
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
    }
}
