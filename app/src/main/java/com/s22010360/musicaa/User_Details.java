package com.s22010360.musicaa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import java.util.HashMap;
import java.util.Map;

public class User_Details extends AppCompatActivity {
    EditText txtName, txtCountry, txtPhoneNumber;
    Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        txtName = findViewById(R.id.user_Name);
        txtPhoneNumber = findViewById(R.id.tele_Num);
        txtCountry = findViewById(R.id.countryE);
        btnContinue = findViewById(R.id.btnContinue);

        addData();
    }

    public void addData() {
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtName.getText().toString().trim();
                String phoneNumber = txtPhoneNumber.getText().toString().trim();
                String country = txtCountry.getText().toString().trim();

                // Validate input fields
                if (name.isEmpty() || phoneNumber.isEmpty() || country.isEmpty()) {
                    Toast.makeText(User_Details.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                    return;
                }

                // Get current user ID from Firebase Authentication
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    String userId = user.getUid();

                    // Get reference to the Firestore instance
                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    // Create a HashMap to store the user details
                    Map<String, Object> userDetails = new HashMap<>();
                    userDetails.put("userId", userId); // Save specific user ID
                    userDetails.put("Name", name);
                    userDetails.put("Country", country);
                    userDetails.put("phoneNumber", phoneNumber);

                    // Set the user details in Firestore
                    db.collection("userInformation").document(userId)
                            .set(userDetails, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Data successfully saved
                                    Toast.makeText(User_Details.this, "Thank you for providing your details", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(User_Details.this, Home.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Failed to save data
                                    Toast.makeText(User_Details.this, "Error occurred: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                } else {
                    // User is not authenticated, handle accordingly (e.g., prompt user to log in)
                    Toast.makeText(User_Details.this, "User not authenticated", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
