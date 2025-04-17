package com.s22010075.osuhalapharmacyapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Shop extends AppCompatActivity {

    // button variable
    private ImageView navBtnHome, navBtnUpdates, navBtnLocation, navBtnHealthCare, navBtnProfile;
    private Button btnAll, btnTablets, btnLiquids, btnSyrups, btnOils, btnCreams, btnDrops, btnAdultBabyCare,
            btnPetCare, btnBeautyCare, btnFirstAid, btnOther, btnGlossary, btnEquipment;
    private TextView categoryName;

    // for shop items
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ItemAdapterForStore itemAdapterForStore;
    ArrayList<DataModelClassForStore> dataListForStore;

    // for searching
    SearchView searchViewOnShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        // get and set category name
        Intent intentCategory = getIntent();
        String intentCategoryName = intentCategory.getStringExtra("categoryName");
        String categoryParentValue = intentCategory.getStringExtra("categoryParent");
        String categoryValue = intentCategory.getStringExtra("categoryValue");
        categoryName = findViewById(R.id.categoryTopic);
        categoryName.setText(intentCategoryName);

        // -----------------------------------------------------------------------------------------
        // Search view focusing - when the dashboard search bar pressing
        searchViewOnShop = findViewById(R.id.searchBarForStore);

        // Retrieve the extra parameter from intent
        boolean focusOnSearch = getIntent().getBooleanExtra("focusOnSearch", false);
        if (focusOnSearch) {
            categoryName.setText("All"); // setting category name
            // Request focus for the SearchView
            searchViewOnShop.requestFocus();
            // Optionally show the keyboard immediately
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(searchViewOnShop, InputMethodManager.SHOW_IMPLICIT);
        }

        // -----------------------------------------------------------------------------------------
        // firebase data retrieval

        // category btn
        // connect with UI
        btnAll = findViewById(R.id.allCategoryBtn);
        btnTablets = findViewById(R.id.tabletsCategoryBtn);
        btnLiquids = findViewById(R.id.liquidsCategoryBtn);
        btnSyrups = findViewById(R.id.syrupsCategoryBtn);
        btnOils = findViewById(R.id.oilsCategoryBtn);
        btnCreams = findViewById(R.id.creamsCategoryBtn);
        btnDrops = findViewById(R.id.dropsCategoryBtn);
        btnAdultBabyCare = findViewById(R.id.adultNBabyCategoryBtn);
        btnPetCare = findViewById(R.id.petCareCategoryBtn);
        btnBeautyCare = findViewById(R.id.beautyCategoryBtn);
        btnFirstAid = findViewById(R.id.firstAidCategoryBtn);
        btnGlossary = findViewById(R.id.glossaryCategoryBtn);
        btnEquipment = findViewById(R.id.equipmentCategoryBtn);
        btnOther = findViewById(R.id.otherCategoryBtn);

        // for shop items
        recyclerView = findViewById(R.id.recyclerViewForShop);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));  // grid with 2 columns

        // initializing AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(Shop.this);
        builder.setCancelable(false);
        builder.setView(R.layout.layout_progress_view);
        AlertDialog dialog = builder.create();
        dialog.show();

        // check if internet is connected
        if (!isInternetConnected.isConnectedToInternet(this)) {
            showErrorMessageToCheckingInternet();
            dialog.dismiss();
            return;
        }

        // firebase connection
        try {
            // connect with Firebase Realtime Database
            databaseReference = FirebaseDatabase.getInstance().getReference(categoryParentValue).child(String.valueOf(categoryValue));
            dataListForStore = new ArrayList<>();
            itemAdapterForStore = new ItemAdapterForStore(this, dataListForStore);
            recyclerView.setAdapter(itemAdapterForStore);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        DataModelClassForStore dataModelClassForStore = dataSnapshot.getValue(DataModelClassForStore.class);
                        dataListForStore.add(dataModelClassForStore);
                    }
                    itemAdapterForStore.notifyDataSetChanged();
                    dialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Shop.this, "An error occurred with the Database", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
        } catch (Exception e) {
            Toast.makeText(Shop.this, "An error occurred while connecting to the Database", Toast.LENGTH_SHORT).show();
            dialog.dismiss();

            // open Dashboard window
            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
            startActivity(intent);
        }

        // Dismiss the dialog after 10 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 10000); // 10 000 milliseconds = 10 seconds

        // -----------------------------------------------------------------------------------------
        // searching
        searchViewOnShop.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                databaseReference = FirebaseDatabase.getInstance().getReference(categoryParentValue).child(String.valueOf(categoryValue));
                performSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch(newText);
                categoryName.setText(" ");
                return true;
            }
        });

        // -----------------------------------------------------------------------------------------
        // category btn actions

        // setting category name
        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to set category name
                categoryName.setText("All Items");
                Intent intent = new Intent(getApplicationContext(), Shop.class);
                intent.putExtra("categoryParent", "All Shop");
                intent.putExtra("categoryValue", "All");
                intent.putExtra("categoryName", "All");
                startActivity(intent);

            }
        });

        btnTablets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to set category name
                categoryName.setText("Tablets");
                Intent intent = new Intent(getApplicationContext(), Shop.class);
                intent.putExtra("categoryName", "Tablets");
                intent.putExtra("categoryParent", "Shop");
                intent.putExtra("categoryValue", "Tablets");
                startActivity(intent);
            }
        });

        btnLiquids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to set category name
                categoryName.setText("Liquids");
                Intent intent = new Intent(getApplicationContext(), Shop.class);
                intent.putExtra("categoryParent", "Shop");
                intent.putExtra("categoryValue", "Liquids");
                intent.putExtra("categoryName", "Liquids");
                startActivity(intent);
            }
        });

        btnSyrups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to set category name
                categoryName.setText("Syrups");
                Intent intent = new Intent(getApplicationContext(), Shop.class);
                intent.putExtra("categoryParent", "Shop");
                intent.putExtra("categoryValue", "Syrup");
                intent.putExtra("categoryName", "Syrup");
                startActivity(intent);
            }
        });

        btnOils.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to set category name
                categoryName.setText("Oils");
                Intent intent = new Intent(getApplicationContext(), Shop.class);
                intent.putExtra("categoryParent", "Shop");
                intent.putExtra("categoryValue", "Oils");
                intent.putExtra("categoryName", "Oils");
                startActivity(intent);
            }
        });

        btnCreams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to set category name
                categoryName.setText("Creams & Bams");
                Intent intent = new Intent(getApplicationContext(), Shop.class);
                intent.putExtra("categoryParent", "Shop");
                intent.putExtra("categoryValue", "Creams & Bams");
                intent.putExtra("categoryName", "Creams & Bams");
                startActivity(intent);
            }
        });

        btnDrops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to set category name
                categoryName.setText("Drops");
                Intent intent = new Intent(getApplicationContext(), Shop.class);
                intent.putExtra("categoryParent", "Shop");
                intent.putExtra("categoryValue", "Drops");
                intent.putExtra("categoryName", "Drops");
                startActivity(intent);
            }
        });

        btnAdultBabyCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to set category name
                categoryName.setText("Baby Care");
                Intent intent = new Intent(getApplicationContext(), Shop.class);
                intent.putExtra("categoryParent", "Shop");
                intent.putExtra("categoryValue", "Baby Care");
                intent.putExtra("categoryName", "Baby Care");
                startActivity(intent);

            }
        });

        btnPetCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to set category name
                categoryName.setText("Pet Care");
                Intent intent = new Intent(getApplicationContext(), Shop.class);
                intent.putExtra("categoryParent", "Shop");
                intent.putExtra("categoryValue", "Pet Care");
                intent.putExtra("categoryName", "Pet Care");
                startActivity(intent);
            }
        });

        btnBeautyCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to set category name
                categoryName.setText("Beauty Care");
                Intent intent = new Intent(getApplicationContext(), Shop.class);
                intent.putExtra("categoryParent", "Shop");
                intent.putExtra("categoryValue", "Beauty");
                intent.putExtra("categoryName", "Beauty Care");
                startActivity(intent);
            }
        });

        btnFirstAid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to set category name
                categoryName.setText("First Aid");
                Intent intent = new Intent(getApplicationContext(), Shop.class);
                intent.putExtra("categoryParent", "Shop");
                intent.putExtra("categoryValue", "First Aid");
                intent.putExtra("categoryName", "First Aid");
                startActivity(intent);
            }
        });

        btnEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to set category name
                categoryName.setText("Equipment");
                Intent intent = new Intent(getApplicationContext(), Shop.class);
                intent.putExtra("categoryParent", "Shop");
                intent.putExtra("categoryValue", "Equipments");
                intent.putExtra("categoryName", "Equipments");
                startActivity(intent);
            }
        });

        btnGlossary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to set category name
                categoryName.setText("Glossary");
                Intent intent = new Intent(getApplicationContext(), Shop.class);
                intent.putExtra("categoryParent", "Shop");
                intent.putExtra("categoryValue", "Glossary");
                intent.putExtra("categoryName", "Glossary");
                startActivity(intent);
            }
        });

        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to set category name
                categoryName.setText("Other Items");
                Intent intent = new Intent(getApplicationContext(), Shop.class);
                intent.putExtra("categoryParent", "Shop");
                intent.putExtra("categoryValue", "Suppliment");
                intent.putExtra("categoryName", "Other");
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

    // ---------------------------------------------------------------------------------------------
    // searching method
    private void performSearch(String query) {
        if (query.isEmpty()) {
            dataListForStore.clear();
            itemAdapterForStore.notifyDataSetChanged();
            return;
        }

        Query searchQuery = databaseReference.orderByChild("name").startAt(query).endAt(query + "\uf8ff");
        searchQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataListForStore.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DataModelClassForStore item = snapshot.getValue(DataModelClassForStore.class);
                    dataListForStore.add(item);
                }
                itemAdapterForStore.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Log.d("Shop", "Error getting data: ", databaseError.toException());
                Toast.makeText(Shop.this, "An error with searching.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ---------------------------------------------------------------------------------------------
    // internet connection checking method
    public static class isInternetConnected {

        public static boolean isConnectedToInternet(Context context) {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connectivityManager != null) {
                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                return activeNetwork != null && activeNetwork.isConnected();
            }

            return false;
        }
    }

    // checking internet message
    private void showErrorMessageToCheckingInternet() {
        View view = findViewById(android.R.id.content);
        // Snackbar.make(view, "No internet connection available", Snackbar.LENGTH_LONG).show();
        Toast.makeText(this, "No internet connection available", Toast.LENGTH_LONG).show();
    }
}