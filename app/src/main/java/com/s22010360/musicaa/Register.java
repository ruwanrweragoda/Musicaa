package com.s22010360.musicaa;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    // Declare the views and FirebaseAuth instance
    Button btnRegister;
    EditText email;
    EditText password;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth instance
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize views
        btnRegister = findViewById(R.id.btnRegister);
        email = findViewById(R.id.regEmail);
        password = findViewById(R.id.regPassword);

        // Register button click listener
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get email and password from EditTexts
                String userEmail = email.getText().toString().trim();
                String userPassword = password.getText().toString().trim();

                // Validate email and password
                if (validateInput(userEmail, userPassword)) {
                    // Register user with Firebase
                    firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                            .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Registration success, navigate to login activity
                                        startActivity(new Intent(Register.this, User_Details.class));
                                        finish();
                                    } else {
                                        // Registration failed, show error message
                                        Toast.makeText(Register.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    // Validate the email and password inputs
    private boolean validateInput(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Password validation: At least one digit, one uppercase, one lowercase, and minimum 8 characters
        if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$")) {
            Toast.makeText(this, "Password must contain at least one digit, one uppercase letter, one lowercase letter, and be at least 8 characters long", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
