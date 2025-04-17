package com.s22010075.osuhalapharmacyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LogIn extends AppCompatActivity {

    // declare variables
    EditText logInUsername, logInPassword;
    TextView signInText;
    Button logInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        // connect variables with UI
        logInUsername = findViewById(R.id.userNameInput);
        logInPassword = findViewById(R.id.passwordInput);
        logInButton = findViewById(R.id.btnSignInLogIn);
        signInText = findViewById(R.id.textViewSignInLogIn);

        // log in button action
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateUsername() | !validatePassword()) {
                    Toast.makeText(LogIn.this, "Fields cannot be empty.", Toast.LENGTH_SHORT).show();
                } else {
                    checkUser();
                }
            }
        });

        // sign in text action to move into sign in window
        signInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this, SignIn.class);
                startActivity(intent);
            }
        });
    }

    // validate username
    public Boolean validateUsername() {
        String validate = logInUsername.getText().toString();
        if (validate.isEmpty()) {
            logInUsername.setError("Username cannot be empty");
            return false;
        } else {
            logInUsername.setError(null);
            return true;
        }
    }

    // validate password
    public Boolean validatePassword() {
        String validate = logInPassword.getText().toString();
        if (validate.isEmpty()) {
            logInPassword.setError("Password cannot be empty");
            return false;
        } else {
            logInPassword.setError(null);
            return true;
        }
    }

    // check the user
    public void checkUser() {
        // get user values
        String userUsername = logInUsername.getText().toString().trim();
        String userPassword = logInPassword.getText().toString().trim();

        // connect to firebase database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkUserDatabase = databaseReference.orderByChild("username").equalTo(userUsername);

        // Add listener to handle the result
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // check if username exists
                if (snapshot.exists()) {
                    // iterate over each child
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        // retrieve the password from the snapshot
                        String passwordFromDB = userSnapshot.child("password").getValue(String.class);

                        // check if the provided password matches the retrieved password
                        if (Objects.equals(passwordFromDB, userPassword)) {
                            logInUsername.setError(null);
                            Toast.makeText(LogIn.this, "Log In successful!", Toast.LENGTH_SHORT).show();

                            // retrieve additional details
                            String nameFromDB = userSnapshot.child("name").getValue(String.class);
                            String emailFromDB = userSnapshot.child("email").getValue(String.class);
                            String imageUrlFromDB = userSnapshot.child("imageUrl").child("imageUrl").getValue(String.class);

                            // save login state --
                            SharedPreferences sharedPreferences = getSharedPreferences("myAppPreferences", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("isLoggedIn", true);
                            editor.putString("username", userUsername);
                            editor.putString("email", emailFromDB);
                            editor.putString("name", nameFromDB);
                            editor.putString("password", userPassword);
                            editor.putString("imageUrl", imageUrlFromDB);
                            editor.apply();

                            // open Profile window
                            Intent intent = new Intent(LogIn.this, Profile.class);
                            startActivity(intent);
                        } else {
                            logInPassword.setError("Invalid Credentials");
                            logInPassword.requestFocus();
                            Toast.makeText(LogIn.this, "Invalid Credentials. Try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    logInUsername.setError("User does not exist");
                    logInUsername.requestFocus();
                    Toast.makeText(LogIn.this, "User does not exist. Try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}