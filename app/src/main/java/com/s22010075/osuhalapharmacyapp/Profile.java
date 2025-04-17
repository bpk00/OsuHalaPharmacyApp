package com.s22010075.osuhalapharmacyapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URL;

public class Profile extends AppCompatActivity {

    //button variable
    private ImageView navBtnHome, navBtnUpdates, navBtnLocation, navBtnHealthCare, navBtnProfile;
    // text variables
    private TextView usernameDisplayText, nameDisplayText, emailDisplayText;

    // for upload photo
    ImageView uploadProfilePhoto, editProfilePhoto;
    String profilePhotoUrl, initialProfilePhotoUrl;
    Uri profilePhotoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // check login state
        SharedPreferences sharedPreferences = getSharedPreferences("myAppPreferences", MODE_PRIVATE);

        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (!isLoggedIn) {
            // display toast message
            Toast.makeText(Profile.this, "You're not logged in. Please log in.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Profile.this, LogIn.class);
            startActivity(intent);
            finish();
        } else {
            // load user details and show profile
            String username = sharedPreferences.getString("username", "");
            String name = sharedPreferences.getString("name", "");
            String email = sharedPreferences.getString("email", "");
            String imageUrl = sharedPreferences.getString("imageUrl", "");

            // Store the initial profile photo URL
            initialProfilePhotoUrl = imageUrl;

            // initialize the TextView
            usernameDisplayText = findViewById(R.id.usernameDisplayText);
            nameDisplayText = findViewById(R.id.nameDisplayText);
            emailDisplayText = findViewById(R.id.emailDisplayText);

            // Display the username, name and email
            usernameDisplayText.setText(username);
            nameDisplayText.setText(name);
            emailDisplayText.setText(email);

            // display profile photo
            uploadProfilePhoto = findViewById(R.id.profilePhotoShape);
            if (imageUrl != null) {
                // Load the image using Glide
                Glide.with(this)
                        .load(imageUrl)
                        .circleCrop()
                        .placeholder(R.drawable.customer_circle_for_profile) // Set placeholder image
                        .error(R.drawable.customer_circle_for_profile) // Set error image
                        .into(uploadProfilePhoto);
            }
        }

        // upload profile photo
        uploadProfilePhoto = findViewById(R.id.profilePhotoShape);
        editProfilePhoto = findViewById(R.id.editProfilePhotoShape);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            profilePhotoUri = data.getData();
                            //uploadProfilePhoto.setImageURI(profilePhotoUri);

                            // display profile photo
                            uploadProfilePhoto = findViewById(R.id.profilePhotoShape);
                            if (profilePhotoUri != null) {
                                // Load the image using Glide
                                Glide.with(Profile.this)
                                        .load(profilePhotoUri)
                                        .circleCrop()
                                        .placeholder(R.drawable.customer_circle_for_profile) // Set placeholder image
                                        .error(R.drawable.customer_circle_for_profile) // Set error image
                                        .into(uploadProfilePhoto);
                            }
                            // for save
                            String username = sharedPreferences.getString("username", "");
                            showSaveAlertDialog(username);
                        } else {
                            Toast.makeText(Profile.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        editProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        // -----------------------------------------------------------------------------------------

        // screen buttons
        // screen button - to move into wishlist window
        Button btnWishlist = findViewById(R.id.myWishListBtn);
        btnWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Wishlist.class);
                startActivity(intent);
            }
        });

        // screen button - to move into log in  window
        Button btnLogIn = findViewById(R.id.logInbtn);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LogIn.class);
                startActivity(intent);
            }
        });

        // screen button - to move into sign in  window
        Button btnSignIn = findViewById(R.id.signInBtn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignIn.class);
                startActivity(intent);
            }
        });

        // screen button - to move into edit profile  window
        Button btnEditProfile = findViewById(R.id.editProfileBtn);
        Button btnChangePassword = findViewById(R.id.changePasswordBtn);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfile.class);
                startActivity(intent);
            }
        });
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfile.class);
                startActivity(intent);
            }
        });

        // to move into edit profile  window with edit icon in name field and email field
        ConstraintLayout editNameIconLayout = findViewById(R.id.editNameIconLayout);
        ConstraintLayout editEmailIconLayout = findViewById(R.id.editEmailIconLayout);
        editNameIconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfile.class);
                startActivity(intent);
            }
        });
        editEmailIconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfile.class);
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

    // for save profile photo
    private void showSaveAlertDialog(String username) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save Work");
        builder.setMessage("Do you want to save your changes?");

        // Set up the save in alert
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User clicked save in alert
                saveWork(username);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User clicked cancel in alert
                // Restore the initial profile photo if the user cancels saving
                Glide.with(Profile.this)
                        .load(initialProfilePhotoUrl)
                        .circleCrop()
                        .placeholder(R.drawable.customer_circle_for_profile) // Set placeholder image
                        .error(R.drawable.customer_circle_for_profile) // Set error image
                        .into(uploadProfilePhoto);

                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void saveWork(String username) {
        // save logic
        StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child("Users").child(username).child("imageUrl");
        // profilePhotoUri.getLastPathSegment()

        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
        builder.setCancelable(false);
        builder.setView(R.layout.layout_progress_view_saving);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(profilePhotoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                profilePhotoUrl = urlImage.toString();
                uploadData(username);
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                dialog.dismiss();
                Toast.makeText(Profile.this, "Failed to upload image: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // for save url of image
    public void uploadData(String username) {

        FirebaseHelperClass firebaseHelperClass = new FirebaseHelperClass(profilePhotoUrl);

        FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("imageUrl")
                .setValue(firebaseHelperClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Profile.this, "Saved", Toast.LENGTH_SHORT).show();
                            showLoginAgainAlertDialog(); // for login again alert
                            //finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Profile.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // for telling login again
    private void showLoginAgainAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Login Again");
        builder.setMessage("Do you want to see the saved changes?");

        // Set up the save in alert
        builder.setPositiveButton("Goto Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User clicked login in alert
                Intent intent = new Intent(Profile.this, LogIn.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User clicked cancel in alert
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}