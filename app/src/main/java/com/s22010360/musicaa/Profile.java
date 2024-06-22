package com.s22010360.musicaa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;


public class Profile extends AppCompatActivity {
    private ImageButton Home;
    private ImageButton Explore;
    private ImageButton Profile;
    private Button Followers, BtnLogout;
    private Button Following;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        BtnLogout = findViewById(R.id.logOut);
        BtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logout user
                firebaseAuth.signOut();

                // Redirect to login activity
                startActivity(new Intent(Profile.this, Login.class));
                finish();
            }
        });



        Home = findViewById(R.id.home);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, Home.class);
                startActivity(intent);
            }
        });
        Explore = findViewById(R.id.Explore);
        Explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, Explore.class);
                startActivity(intent);
            }
        });
        Profile = findViewById(R.id.Profile);
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, Profile.class);
                startActivity(intent);

            }
        });
        Followers = findViewById(R.id.followers);
        Followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, Follower_list.class);
                startActivity(intent);

            }
        });
        Following = findViewById(R.id.followings);
        Following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, Folowing_list.class);
                startActivity(intent);

            }
        });
    }
}