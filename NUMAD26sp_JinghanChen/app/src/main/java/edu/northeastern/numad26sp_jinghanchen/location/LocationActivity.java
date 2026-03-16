package edu.northeastern.numad26sp_jinghanchen.location;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

import edu.northeastern.numad26sp_jinghanchen.R;

public class LocationActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private TextView textLatLng;
    private TextView textDistance;
    private Location lastLocation;
    private float totalDistance = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        textLatLng = findViewById(R.id.textLatLng);
        textDistance = findViewById(R.id.textDistance);

        if (savedInstanceState != null) {
            totalDistance = savedInstanceState.getFloat("totalDistance");
            lastLocation = savedInstanceState.getParcelable("lastLocation");
            textDistance.setText("Total Distance: " + String.format("%.2f", totalDistance) + " m");
        }

        Button buttonReset = findViewById(R.id.buttonReset);
        buttonReset.setOnClickListener(v -> {
            totalDistance = 0f;
            lastLocation = null;
            textDistance.setText("Total Distance: 0.00 m");
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                new AlertDialog.Builder(LocationActivity.this)
                        .setTitle("Discard Distance?")
                        .setMessage("Your total distance will be lost if you go back.")
                        .setPositiveButton("Discard", (dialog, which) -> finish())
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                        .show();
            }
        });

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000)
                .setMinUpdateIntervalMillis(2000)
                .build();

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) return;
                Location newLocation = locationResult.getLastLocation();
                if (newLocation == null) return;
                double lat = newLocation.getLatitude();
                double lng = newLocation.getLongitude();
                textLatLng.setText("Latitude: " + lat + "\nLongitude: " + lng);

                if (lastLocation != null) {
                    float delta = lastLocation.distanceTo(newLocation);
                    // ignore dis increase if the distance is less than 10 meters,
                    // this is to cope with gps inaccuracy.
                    if (delta > 5.0f) {
                        totalDistance += delta;
                        textDistance.setText("Total Distance: " + String.format("%.2f", totalDistance) + " m");
                        lastLocation = newLocation;
                    }
                } else {
                    lastLocation = newLocation;
                }
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat("totalDistance", totalDistance);
        outState.putParcelable("lastLocation", lastLocation);
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) return;
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                Toast.makeText(this, "Location permission is required", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
