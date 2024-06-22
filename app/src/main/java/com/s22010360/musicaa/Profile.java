package com.s22010360.musicaa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Profile extends AppCompatActivity {
    private ImageButton Home, Explore, Profile;
    private Button Followers, BtnLogout, Following;
    private FirebaseAuth firebaseAuth;
    private TextView name, countryName;
    FirebaseFirestore db;
    DocumentReference userDocRef;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        BtnLogout = findViewById(R.id.logOut);
        name = findViewById(R.id.name);
        countryName = findViewById(R.id.nameOfcountry);
        db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            // User is signed in
            userId = currentUser.getUid();
            userDocRef = db.collection("userInformation").document(userId);

            // Load user data from Firestore
            userDocRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    // User data exists
                    String userName = documentSnapshot.getString("Name");
                    String userCountryName = documentSnapshot.getString("Country");

                    // Display user details in TextViews
                    if (userName != null) {
                        name.setText(userName);
                    }
                    if (userCountryName != null) {
                        countryName.setText(userCountryName);
                    }
                }
            });
        }

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
