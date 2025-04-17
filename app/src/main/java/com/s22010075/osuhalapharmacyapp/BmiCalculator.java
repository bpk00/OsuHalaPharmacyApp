package com.s22010075.osuhalapharmacyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BmiCalculator extends AppCompatActivity {

    //button variable
    private ImageView navBtnHome, navBtnUpdates, navBtnLocation, navBtnHealthCare, navBtnProfile;

    // for BMI calculator
    private EditText weightField, heightField;
    private Button calculateBtn;
    private TextView outputText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator);

        // calculate BMI
        // Initialize UI components
        weightField = findViewById(R.id.weightField);
        heightField = findViewById(R.id.heightField);
        calculateBtn = findViewById(R.id.calculateBtn);
        outputText = findViewById(R.id.outputText);

        // Set click listener for calculate button
        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBmi();
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

    // methods for calculating BMI
    private void calculateBmi() {
        String weightStr = weightField.getText().toString();
        String heightStr = heightField.getText().toString();

        if (weightStr.isEmpty() || heightStr.isEmpty()) {
            outputText.setText("Please enter both weight and height.");
            return;
        }

        double weight = Double.parseDouble(weightStr);
        double height = Double.parseDouble(heightStr) / 100; // convert height from cm to meters

        double bmi = weight / (height * height);

        String bmiCategory;
        String recommendations;
        if (bmi < 18.5) {
            bmiCategory = "Underweight";
            recommendations = "Consider eating more frequent, nutrient-rich meals and incorporating strength training exercises.";
        } else if (bmi >= 18.5 && bmi < 24.9) {
            bmiCategory = "Normal weight";
            recommendations = "Maintain a balanced diet and regular physical activity to keep your BMI in this range.";
        } else if (bmi >= 25 && bmi < 29.9) {
            bmiCategory = "Overweight";
            recommendations = "Consider a healthy eating plan and regular physical activity to help lose weight.";
        } else {
            bmiCategory = "Obesity";
            recommendations = "Consult with a healthcare provider for personalized advice and a plan to lose weight safely.";
        }

        String resultText = String.format("Your BMI is %.2f. You are in the %s category. %s", bmi, bmiCategory, recommendations);
        outputText.setText(resultText);
    }
}