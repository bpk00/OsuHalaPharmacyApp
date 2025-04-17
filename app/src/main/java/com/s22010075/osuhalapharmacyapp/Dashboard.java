package com.s22010075.osuhalapharmacyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Dashboard extends AppCompatActivity {

    //button variable
    private ImageView navBtnHome, navBtnUpdates, navBtnLocation, navBtnHealthCare, navBtnProfile;
    private TextView btnMedicine, btnServices, btnGlossary, btnEquipment, btnPharmacies, btnOther;
    private TextView btnSearchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //btn search bar
        btnSearchBar = findViewById(R.id.searchField);
        btnSearchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to open window with search view focus on searching
                Intent intent = new Intent(getApplicationContext(), Shop.class);
                intent.putExtra("categoryParent", "All Shop");
                intent.putExtra("categoryValue", "All");
                intent.putExtra("categoryName", "All");
                intent.putExtra("focusOnSearch", true);
                startActivity(intent);
            }
        });

        // content buttons
        // Medicine - to move into medicine window
        btnMedicine = findViewById(R.id.btnMedicine);
        btnMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to open window
                Intent intent = new Intent(getApplicationContext(), Shop.class);
                startActivity(intent);
                // to set category name
                Intent intentCategory = new Intent(Dashboard.this, Shop.class);
                intentCategory.putExtra("categoryValue", "Tablets");
                intentCategory.putExtra("categoryParent", "Shop");
                intentCategory.putExtra("categoryName", "Medicine");
                startActivity(intentCategory);
            }
        });

        // Services - to move into services window
        btnServices = findViewById(R.id.btnServices);
        btnServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Services.class);
                startActivity(intent);
            }
        });

        // Glossary - to move into glossary window
        btnGlossary = findViewById(R.id.btnGlossary);
        btnGlossary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Shop.class);
                startActivity(intent);
                // to set category name
                Intent intentCategory = new Intent(Dashboard.this, Shop.class);
                intentCategory.putExtra("categoryName", "Glossary");
                intentCategory.putExtra("categoryValue", "Glossary");
                intentCategory.putExtra("categoryParent", "Shop");
                startActivity(intentCategory);
            }
        });

        // Equipment - to move into equipment window
        btnEquipment = findViewById(R.id.btnEquipments);
        btnEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Shop.class);
                startActivity(intent);
                // to set category name
                Intent intentCategory = new Intent(Dashboard.this, Shop.class);
                intentCategory.putExtra("categoryName", "Equipment");
                intentCategory.putExtra("categoryValue", "Equipments");
                intentCategory.putExtra("categoryParent", "Shop");
                startActivity(intentCategory);
            }
        });

        // Pharmacies - to move into pharmacies window
        btnPharmacies = findViewById(R.id.btnPharmacies);
        btnPharmacies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Pharmacies.class);
                startActivity(intent);
            }
        });

        // Other - to move into other window
        btnOther = findViewById(R.id.btnOtherDashboard);
        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OtherWindow.class);
                startActivity(intent);
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
}