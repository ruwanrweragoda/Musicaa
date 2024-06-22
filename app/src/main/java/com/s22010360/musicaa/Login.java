package com.s22010360.musicaa;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    // Declare the views and FirebaseAuth instance
    private Button btnLogin, registerbtn;
    private EditText email, password;
    private FirebaseAuth firebaseAuth;

    // Check if user is logged in (non-null) and update UI to Home activity.
    @Override
    public void onStart() {
        super.onStart();
        // Initialize Firebase Auth instance here to avoid null pointer exception
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            // If user is already logged in, navigate to Home activity
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth instance
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize views
        btnLogin = findViewById(R.id.btnLogin);
        registerbtn = findViewById(R.id.buttonRegister); // Fixed ID reference
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        // Log statements to debug view initialization
        Log.d("LoginActivity", "btnLogin initialized: " + (btnLogin != null));
        Log.d("LoginActivity", "registerbtn initialized: " + (registerbtn != null));
        Log.d("LoginActivity", "email initialized: " + (email != null));
        Log.d("LoginActivity", "password initialized: " + (password != null));

        // Navigate to RegisterActivity
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        // Login button click listener
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString().trim();
                String userPassword = password.getText().toString().trim();

                // Validate email and password
                if (validateInput(userEmail, userPassword)) {
                    // Authenticate user with Firebase
                    firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Login success, navigate to Home activity
                                        startActivity(new Intent(Login.this, Home.class));
                                        finish();
                                    } else {
                                        // Login failed, show error message
                                        Toast.makeText(Login.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
