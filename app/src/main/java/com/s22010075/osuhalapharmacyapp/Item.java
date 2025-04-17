package com.s22010075.osuhalapharmacyapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class Item extends AppCompatActivity {

    // button variable
    private ImageView navBtnHome, navBtnUpdates, navBtnLocation, navBtnHealthCare, navBtnProfile;

    // for displaying details of item
    ImageView itemImage;
    TextView  itemName, itemPrice, itemAvailability, itemDescription, itemKeywords ,itemRating;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        // for displaying details of item
//        itemImage = findViewById(R.id.itemImage);
//        itemName = findViewById(R.id.itemName);
//        itemPrice = findViewById(R.id.itemName);
//        itemAvailability = findViewById(R.id.itemName);
//        itemDescription = findViewById(R.id.itemName);
//        itemKeywords = findViewById(R.id.itemName);
//        itemRating = findViewById(R.id.itemName);
//
//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            //itemImage.setImageURI(Uri.parse(bundle.getString("Image")));
//            Glide.with(this).load(bundle.getString("Image")).into(itemImage);
//            itemName.setText(bundle.getString("Name"));
//            itemPrice.setText(bundle.getString("Price"));
//            itemAvailability.setText(bundle.getString("Availability"));
//            itemDescription.setText(bundle.getString("Description"));
//            itemKeywords.setText(bundle.getString("Keywords"));
//            itemRating.setText(bundle.getString("Rating"));
//        }

        // -----------------------------------------------------------------------------------------

        // nav bar icon buttons
        // Home - to move into dashboard window
        navBtnHome = findViewById(R.id.iconHome);
        navBtnHome.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
            startActivity(intent);
        });

        // Updates - to move into updates window
        navBtnUpdates = findViewById(R.id.iconNotice);
        navBtnUpdates.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Updates.class);
            startActivity(intent);
        });

        // Location - to move into location window
        navBtnLocation = findViewById(R.id.iconLocation);
        navBtnLocation.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Location.class);
            startActivity(intent);
        });

        // HealthCare - to move into healthcare window
        navBtnHealthCare = findViewById(R.id.iconHealth);
        navBtnHealthCare.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HealthCare.class);
            startActivity(intent);
        });

        // Profile - to move into profile window
        navBtnProfile = findViewById(R.id.iconProfile);
        navBtnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Profile.class);
            startActivity(intent);
        });
    }
}