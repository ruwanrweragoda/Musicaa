package com.s22010360.musicaa;

import android.content.Intent;

import androidx.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.MarkerOptions;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.GoogleMap;

public class AbboutUs extends AppCompatActivity implements OnMapReadyCallback {
    private ImageButton Home;
    private ImageButton Explore;
    private ImageButton Profile;
    private GoogleMap FindUsMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_abbout_us);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        FindUsMap = googleMap;
        float zoomLevel = 15;
        double latitude =7.293001431845665;
        double longitude = 80.6488810846572;
        LatLng myPlace = new LatLng(latitude, longitude);
        FindUsMap.addMarker(new MarkerOptions().position(myPlace).title("Buwelikada,Kandy"));
        FindUsMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPlace, zoomLevel));
        FindUsMap.getUiSettings().setZoomControlsEnabled(true);


        Home = findViewById(R.id.home);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AbboutUs.this, Home.class);
                startActivity(intent);
            }
        });
        Explore = findViewById(R.id.Explore);
        Explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AbboutUs.this, Explore.class);
                startActivity(intent);
            }
        });
        Profile = findViewById(R.id.Profile);
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AbboutUs.this, Profile.class);
                startActivity(intent);

            }
        });




    }
}
