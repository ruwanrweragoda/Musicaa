package com.s22010360.musicaa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Folowing_list extends AppCompatActivity {
    private ImageButton Home;
    private ImageButton Explore;
    private ImageButton Profile;
    private Button Followers;
    private Button Following;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_following_list);
        Home = findViewById(R.id.home);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Folowing_list.this, Home.class);
                startActivity(intent);
            }
        });
        Explore = findViewById(R.id.Explore);
        Explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Folowing_list.this, Explore.class);
                startActivity(intent);
            }
        });
        Profile = findViewById(R.id.Profile);
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Folowing_list.this, Profile.class);
                startActivity(intent);

            }
        });
        Followers = findViewById(R.id.followerfollowers);
        Followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Folowing_list.this, Follower_list.class);
                startActivity(intent);

            }
        });
        Following = findViewById(R.id.fllowerfollowings);
        Following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Folowing_list.this, Folowing_list.class);
                startActivity(intent);

            }
        });

    }
}