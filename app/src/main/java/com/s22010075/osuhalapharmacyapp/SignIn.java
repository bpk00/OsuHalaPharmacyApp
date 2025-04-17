package com.s22010075.osuhalapharmacyapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.ReturnThis;
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

public class SignIn extends AppCompatActivity {

    // declare variables
    EditText signInName, signInUsername, signInEmail, signInPassword, signInConfirmPassword;
    TextView logInText;
    Button signInButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // connect variables with UI
        signInName = findViewById(R.id.nameInput);
        signInUsername = findViewById(R.id.userNameInput);
        signInEmail = findViewById(R.id.emailInput);
        signInPassword = findViewById(R.id.passwordInput);
        signInConfirmPassword = findViewById(R.id.confirmPasswordInput);
        signInButton = findViewById(R.id.btnSignInLogIn);
        logInText = findViewById(R.id.textViewSignInLogIn);

        // sign in button actions
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // connect with Firebase Realtime Database
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Users");

                String name = signInName.getText().toString();
                String username = signInUsername.getText().toString();
                String email = signInEmail.getText().toString();
                String password = signInPassword.getText().toString();
                String confirmPassword = signInConfirmPassword.getText().toString();
                String imageUrl = "-";

                FirebaseHelperClass firebaseHelperClass = new FirebaseHelperClass(name, username, email, password, confirmPassword, imageUrl);

                // check whether fields are empty
                if (name.isEmpty() | username.isEmpty() | email.isEmpty() | password.isEmpty() | confirmPassword.isEmpty()) {
                    Toast.makeText(SignIn.this, "Fields cannot be empty.", Toast.LENGTH_SHORT).show();
                    if (name.isEmpty()) {
                        signInName.setError("Cannot be empty");
                    }
                    if (username.isEmpty()) {
                        signInUsername.setError("Cannot be empty");
                    }
                    if (email.isEmpty()) {
                        signInEmail.setError("Cannot be empty");
                    }
                    if (password.isEmpty()) {
                        signInPassword.setError("Cannot be empty");
                    }
                    if (confirmPassword.isEmpty()) {
                        signInConfirmPassword.setError("Cannot be empty");
                    }
                } else {
                    // check whether passwords are same
                    if (!confirmPassword.equals(password)) {
                        signInPassword.setError("");
                        signInConfirmPassword.setError("Passwords are not matched.");
                    } else {
                        // check length of password
                        if (password.length() < 4){
                            signInPassword.setError("Password needs to contain at least 4 characters.");
                        } else {
                            // check email in correct format
                            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                signInEmail.setError("Email not in correct format.");
                            } else {
                                // check username in correct format
                                if (username.length() >= 1 && username.length() <= 15) {
                                    boolean isValid = true;
                                    for (char c : username.toCharArray()) {
                                        if (!(Character.isLowerCase(c) || Character.isDigit(c) || c == '_')) {
                                            isValid = false;
                                            break;
                                        }
                                    }

                                    // Username is valid
                                    if (isValid) {
                                        // Check whether username already exists
                                        // The username to check
                                        String usernameToCheck;
                                        usernameToCheck = username;

                                        // Check if the username exists
                                        checkIfUsernameExists(usernameToCheck, new UsernameCheckCallback() {
                                            @Override
                                            public void onUsernameChecked(boolean exists) {
                                                // if the username exists
                                                if (exists) {
                                                    Toast.makeText(SignIn.this, "Try again", Toast.LENGTH_SHORT).show();
                                                }
                                                // if the username can use
                                                else if (!exists) {
                                                    databaseReference.child(username).setValue(firebaseHelperClass);
                                                    Toast.makeText(SignIn.this, "You have sign in successfully!", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(SignIn.this, LogIn.class);
                                                    startActivity(intent);
                                                }
                                                // if there is an error
                                                else {
                                                    Toast.makeText(SignIn.this, "There is an error!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        // Username contains invalid characters
                                        signInUsername.setError("Only allow lowercase a-z, 0-9 or underscore (_)");
                                    }
                                } else {
                                    // Username length is outside the allowed range
                                    signInUsername.setError("Usernames should be between 1-15 characters in length.");
                                }
                            }
                        }
                    }
                }
            }
        });

        // for login text to move into login page
        logInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, LogIn.class);
                startActivity(intent);
            }
        });
    }

    // check if username exists in the database
    private void checkIfUsernameExists(String username, UsernameCheckCallback callback) {
        databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean resultIsExists = dataSnapshot.exists();
                if (resultIsExists) {
                    Toast.makeText(SignIn.this, "Username already exists. Please provide another one.", Toast.LENGTH_SHORT).show();
                    callback.onUsernameChecked(true);
                } else {
                    Toast.makeText(SignIn.this, "Username is available.", Toast.LENGTH_SHORT).show();
                    callback.onUsernameChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SignIn.this, "Error in checking username. Please try again.", Toast.LENGTH_SHORT).show();
                // callback.onUsernameChecked(false);
            }
        });
    }

    // Callback interface
    public interface UsernameCheckCallback {
        void onUsernameChecked(boolean exists);
    }
}