package com.s22010075.osuhalapharmacyapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Location extends AppCompatActivity {

    // google map variable
    private EditText startInput, endInput;
    private Button btnSearch;

    //button variable
    private ImageView navBtnHome, navBtnUpdates, navBtnLocation, navBtnHealthCare, navBtnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        // link variables with UI
        startInput = findViewById(R.id.startInput);
        endInput = findViewById(R.id.endInput);
        btnSearch = findViewById(R.id.btnSearch);

        // create the method for button btnSearch
        btnSearch.setOnClickListener(v -> {
            String startingPoint = startInput.getText().toString();
            String endPoint = endInput.getText().toString();

            if (startingPoint.equals("") || endPoint.equals("")) {
                Toast.makeText(this, "Please enter starting and end locations.",
                        Toast.LENGTH_SHORT).show(); // show toast message
            } else {
                getPath(startingPoint,endPoint); // 'getPath' is crated method
            }
        });

        // -----------------------------------------------------------------------------------------

        // nav bar icon buttons
        // Home - to move into dashboard window
        navBtnHome = findViewById(R.id.iconHome);
        navBtnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
            }
        });

        // Updates - to move into updates window
        navBtnUpdates = findViewById(R.id.iconNotice);
        navBtnUpdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Updates.class);
                startActivity(intent);
            }
        });

        // Location - to move into location window
        navBtnLocation = findViewById(R.id.iconLocation);
        navBtnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Location.class);
                startActivity(intent);
            }
        });

        // HealthCare - to move into healthcare window
        navBtnHealthCare = findViewById(R.id.iconHealth);
        navBtnHealthCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HealthCare.class);
                startActivity(intent);
            }
        });

        // Profile - to move into profile window
        navBtnProfile = findViewById(R.id.iconProfile);
        navBtnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });
    }

    private void getPath(String startingPoint, String endPoint) {
        try { // go to provide places interface
            Uri uri = Uri.parse("https://www.google.com/maps/dir/" +  startingPoint + "/" + endPoint);
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } catch (ActivityNotFoundException exception) { // go to download "Google Maps" from PlayStore
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps&hl=en&gl=US");
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}