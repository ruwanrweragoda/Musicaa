package com.s22010360.musicaa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.KeyguardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class Home extends AppCompatActivity {
    private ImageButton homeButton, exploreButton, profileButton, messageButton, aboutButton;
    private Button Sing01;

    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private ConstraintLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mainLayout = findViewById(R.id.main);

        setupBiometricAuthentication();

        setupNavigationButtons();
    }

    private void setupBiometricAuthentication() {
        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(getApplicationContext(), "Device doesn't have fingerprint hardware", Toast.LENGTH_LONG).show();
                mainLayout.setVisibility(View.VISIBLE);
                return;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(getApplicationContext(), "Fingerprint hardware is currently unavailable", Toast.LENGTH_LONG).show();
                mainLayout.setVisibility(View.VISIBLE);
                return;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(getApplicationContext(), "No fingerprint assigned", Toast.LENGTH_LONG).show();
                mainLayout.setVisibility(View.VISIBLE);
                return;
            case BiometricManager.BIOMETRIC_SUCCESS:
                // Hardware and fingerprint enrolled, proceed to prompt
                break;
        }

        Executor executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(Home.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(), "Authentication error: " + errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                mainLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
            }
        });

        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        if (keyguardManager != null && !keyguardManager.isKeyguardSecure()) {
            mainLayout.setVisibility(View.VISIBLE);
        } else {
            mainLayout.setVisibility(View.GONE);
        }

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Tech Project")
                .setDescription("Use FingerPrint To Login")
                .setDeviceCredentialAllowed(true)
                .build();

        // Show the biometric prompt
        biometricPrompt.authenticate(promptInfo);
    }

    private void setupNavigationButtons() {
        homeButton = findViewById(R.id.home);
        exploreButton = findViewById(R.id.Explore);
        profileButton = findViewById(R.id.Profile);
        messageButton = findViewById(R.id.message);
        aboutButton = findViewById(R.id.Abboutus);
        Sing01 = findViewById(R.id.singbtn1);


        homeButton.setOnClickListener(v -> navigateTo(Home.this, Home.class));
        exploreButton.setOnClickListener(v -> navigateTo(Home.this, Explore.class));
        profileButton.setOnClickListener(v -> navigateTo(Home.this, Profile.class));
        messageButton.setOnClickListener(v -> navigateTo(Home.this, Messages.class));
        aboutButton.setOnClickListener(v -> navigateTo(Home.this, AbboutUs.class));
        Sing01.setOnClickListener(v -> navigateTo(Home.this, Bandimu_Suda.class));

    }

    private void navigateTo(AppCompatActivity currentActivity, Class<?> targetActivity) {
        Intent intent = new Intent(currentActivity, targetActivity);
        startActivity(intent);
    }
}
