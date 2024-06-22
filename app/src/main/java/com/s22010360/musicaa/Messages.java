package com.s22010360.musicaa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Messages extends AppCompatActivity {
    private ImageButton Home;
    private ImageButton Explore;
    private ImageButton Profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_messages);
        Home = findViewById(R.id.home);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Messages.this, Home.class);
                startActivity(intent);
            }
        });
        Explore = findViewById(R.id.Explore);
        Explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Messages.this, Explore.class);
                startActivity(intent);
            }
        });
        Profile = findViewById(R.id.Profile);
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Messages.this, Profile.class);
                startActivity(intent);

            }
        });
    }
}