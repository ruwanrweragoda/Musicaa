package com.s22010360.musicaa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity {
    private ImageButton Home, Explore, Profile;
    private Button Followers, BtnLogout, Following;
    private FirebaseAuth firebaseAuth;
    private TextView name, countryName;
    private RecyclerView recordingsRecyclerView;
    private RecordingsAdapter recordingsAdapter;
    private List<String> recordingUrls;
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
        recordingsRecyclerView = findViewById(R.id.recordingsRecyclerView);
        recordingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recordingUrls = new ArrayList<>();
        recordingsAdapter = new RecordingsAdapter(recordingUrls);
        recordingsRecyclerView.setAdapter(recordingsAdapter);

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

                    // Load recordings data
                    List<String> recordings = (List<String>) documentSnapshot.get("Recordings");
                    if (recordings != null) {
                        recordingUrls.clear();
                        recordingUrls.addAll(recordings);
                        recordingsAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        BtnLogout.setOnClickListener(v -> {
            // Logout user
            firebaseAuth.signOut();

            // Redirect to login activity
            startActivity(new Intent(Profile.this, Login.class));
            finish();
        });

        Home = findViewById(R.id.home);
        Home.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, Home.class);
            startActivity(intent);
        });

        Explore = findViewById(R.id.Explore);
        Explore.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, Explore.class);
            startActivity(intent);
        });

        Profile = findViewById(R.id.Profile);
        Profile.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, Profile.class);
            startActivity(intent);
        });

        Followers = findViewById(R.id.followers);
        Followers.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, Follower_list.class);
            startActivity(intent);
        });

        Following = findViewById(R.id.followings);
        Following.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, Folowing_list.class);
            startActivity(intent);
        });
    }
}
