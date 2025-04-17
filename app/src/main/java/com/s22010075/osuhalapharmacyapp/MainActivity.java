package com.s22010075.osuhalapharmacyapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory;

public class MainActivity extends AppCompatActivity {

    //main screen button variable
    private Boolean isButtonClicked = false;
    private ConstraintLayout mainLayout; // for pressing
    private static final long DELAY_MS = 3000; // 3 seconds delay (without pressing)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        // Initialize Firebase AppCheck
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                PlayIntegrityAppCheckProviderFactory.getInstance()
        );

        // main screen button - to move into dashboard window
        mainLayout = findViewById(R.id.main);
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isButtonClicked = true;
                if (isButtonClicked) {
                    Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                    startActivity(intent);
                }
            }
        });

        // Using Handler to delay the intent - automatically move into dashboard window
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isButtonClicked) {
                    Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                    startActivity(intent);
                }
            }
        }, DELAY_MS);
    }
}