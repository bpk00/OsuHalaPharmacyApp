package com.s22010075.osuhalapharmacyapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Wishlist extends AppCompatActivity {

    // define database variable
    DatabaseHelper myDB;
    // variables - linking with DatabaseHelper
    EditText editTextType, editTextName, editTextSpecialNotes, updateData;
    Button btnAddData, btnViewData, btnUpdateData, btnDeleteData, btnClear;

    //button variable for nav bar
    private ImageView navBtnHome, navBtnUpdates, navBtnLocation, navBtnHealthCare, navBtnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        // create a new DatabaseHelper instance
        myDB = new DatabaseHelper(this);

        // linking layout with variables
        editTextType = findViewById(R.id.typeField);
        editTextName = findViewById(R.id.nameField);
        editTextSpecialNotes = findViewById(R.id.specialNotesField);
        updateData = findViewById(R.id.idField);
        btnAddData = findViewById(R.id.addBtn);
        btnViewData = findViewById(R.id.viewBtn);
        btnUpdateData = findViewById(R.id.updateBtn);
        btnDeleteData = findViewById(R.id.deleteBtn);
        btnClear = findViewById(R.id.clearBtn);

        // call the methods
        insertData();
        viewData();
        updateData();
        deleteData();
        clearTextFields();

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

    // btn actions - methods
    // insert data
    public void insertData() {
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDataInserted = myDB.insertData(
                        editTextType.getText().toString(),
                        editTextName.getText().toString(),
                        editTextSpecialNotes.getText().toString());
                if (isDataInserted == true)
                    Toast.makeText(Wishlist.this, "Data Inserted Successfully", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(Wishlist.this, "Data Not Inserted", Toast.LENGTH_LONG).show();
            }
        });
    }

    // view data
    public void viewData() {
        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor results = myDB.viewAllData();
                if (results.getCount() == 0) {
                    showMessage("Error Message : ", "No Data in the Database");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (results.moveToNext()) {
                    buffer.append("ID : " + results.getString(0) + "\n");
                    buffer.append("Type : " + results.getString(1) + "\n");
                    buffer.append("Name :  " + results.getString(2) + "\n");
                    buffer.append("Special Notes :  " + results.getString(3) + "\n\n");

                    showMessage("List of Data : ", buffer.toString());
                }
            }
        });
    }

    // View data in Alert
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    // update data
    public void updateData() {
        btnUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = myDB.updateData(
                        updateData.getText().toString(),
                        editTextType.getText().toString(),
                        editTextName.getText().toString(),
                        editTextSpecialNotes.getText().toString());
                if (isUpdate == true)
                    Toast.makeText(Wishlist.this, " Data Updated ", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(Wishlist.this, " Data not Updated ", Toast.LENGTH_LONG).show();
            }
        });
    }

    // delete data
    public void deleteData() {
        btnDeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deleteDataRows = myDB.deleteData(updateData.getText().toString());
                if (deleteDataRows > 0)
                    Toast.makeText(Wishlist.this, " Data Deleted Successfully ", Toast.LENGTH_LONG ).show();
                else
                    Toast.makeText(Wishlist.this, " Data not Deleted ", Toast.LENGTH_LONG ).show();
            }
        });
    }

    // clear text fields
    public void clearTextFields() {
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // clear text fields
                editTextType.setText("");
                editTextName.setText("");
                editTextSpecialNotes.setText("");
                updateData.setText("");
            }
        });
    }
}