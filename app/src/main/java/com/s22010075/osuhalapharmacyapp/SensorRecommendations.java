package com.s22010075.osuhalapharmacyapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Task1 : while increasing the temperature, the app will give recommendations

public class SensorRecommendations extends AppCompatActivity implements SensorEventListener {

    // define variables
    private TextView textViewTemperature, textViewRecommendation, textTempHigh, textTempMiddle, textTempLow;
    private ImageView temperatureResultImage;
    private SensorManager sensorManager;
    private Sensor temperatureSensor;
    private float dynamicTempValues;

    //button variable for nav bar
    private ImageView navBtnHome, navBtnUpdates, navBtnLocation, navBtnHealthCare, navBtnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_recommendations);

        // variables (linking with UI, sensorManager)
        textViewTemperature = findViewById(R.id.textViewTemperature);
        textViewRecommendation = findViewById(R.id.textViewRecommendation);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        temperatureResultImage = findViewById(R.id.temperatureResultImage);

        // Get the temperature sensor
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        // Check if the sensor is available
        if (temperatureSensor == null) {
            textViewRecommendation.setTextColor(getColor(R.color.black));
            textViewTemperature.setText("Temperature sensor is not available.");
            textViewRecommendation.setText("Unable to provide temperature recommendations.");
            temperatureResultImage.setImageResource(R.drawable.nice_temperature_image);
        } else {
            // Register the sensor listener if the sensor is available
            sensorManager.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

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

    // methods
    @SuppressLint("SetTextI18n")
    public void onSensorChanged(SensorEvent event) {
        // print dynamic temperature values
        dynamicTempValues = event.values[0];

        // recommendation texts
        textViewTemperature.setText("Temperature: " + String.valueOf(dynamicTempValues) + " °C");

        // recommendation - threshold value = 20°C - 30°C
        if (event.values[0] > 32) {
            textViewRecommendation.setTextColor(Color.parseColor("#D20000"));
            textViewRecommendation.setText("Temperature is higher than 32 °C." + "\n\n" +
                    "Stay Hydrated - Drink more water." + "\n" +
                    "Limit Sun Exposure. - Stay indoors at the noon." + "\n" +
                    "Keep Cool. - Use fans or air conditions." + "\n");
            temperatureResultImage.setImageResource(R.drawable.hot_temperature_image);
        } else if (event.values[0] <= 32 && event.values[0] >= 20) {
            textViewRecommendation.setTextColor(Color.parseColor("#1E9A33"));
            textViewRecommendation.setText("Temperature is not bad for health." + "\n\n" +
                    "Nice Weather - Enjoy your day." + "\n\n" +
                    "Additional Recommendations: " + "\n\n" +
                    "Enjoy Outdoor Activities - Engage in activities like walking, jogging, cycling or playing sports." + "\n\n" +
                    "Enjoy Outdoor Hobbies - Enjoy hobbies like gardening, photographing or bird watching." + "\n\n" +
                    "Picnics - Organize picnics with family or friends to enjoy social interaction and good food in a pleasant environment.");
            temperatureResultImage.setImageResource(R.drawable.nice_temperature_image);
        } else if (event.values[0] < 20) {
            textViewRecommendation.setTextColor(getColor(R.color.darkBlue));
            textViewRecommendation.setText("Temperature is lower than 20 °C." + "\n\n" +
                    "Stay Warm - Wear layers of clothing." + "\n" +
                    "Stay Dry. - Keep cloths dry." + "\n" +
                    "Keep Active. - Engage in physical activities." + "\n" +
                    "Eat and Drink Well." + "\n");
            temperatureResultImage.setImageResource(R.drawable.cold_temperature_image);
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    // register and unregister listener
    // for register a listener
    @Override
    protected void onResume() {
        super.onResume();
        if (temperatureSensor != null) {
            sensorManager.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    // for unregister a listener
    @Override
    protected void onPause() {
        super.onPause();
        if (temperatureSensor != null) {
            sensorManager.unregisterListener(this);
        }
    }
}
