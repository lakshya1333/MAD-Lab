package com.example.androidmastertemplate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

/**
 * Page1_Auth.java — Login / Registration Screen
 *
 * Flow: Login → navigate to Page2_Input
 *       Register → insert user → navigate to Page2_Input
 */
public class Page1_Auth extends AppCompatActivity {

    // ── UI References ─────────────────────────────────────
    private EditText etUsername, etEmail, etPassword;
    private TextInputLayout tilEmail;
    private Button   btnLogin, btnRegister;
    private TextView tvToggle;

    // ── Helpers ───────────────────────────────────────────
    private DatabaseHelper  db;
    private ValidationHelper v;

    // ── State ─────────────────────────────────────────────
    private boolean isLoginMode = true; // Toggle between login/register

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1_auth);

        db = DatabaseHelper.getInstance(this);
        v  = new ValidationHelper(this);

        bindViews();
        setupListeners();
    }

    private void bindViews() {
        etUsername  = findViewById(R.id.etUsername);
        etEmail     = findViewById(R.id.etEmail);
        tilEmail    = findViewById(R.id.tilEmail);
        etPassword  = findViewById(R.id.etPassword);
        btnLogin    = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        tvToggle    = findViewById(R.id.tvToggle);
    }

    private void setupListeners() {
        // ── Login Button ──────────────────────────────────
        btnLogin.setOnClickListener(view -> handleLogin());

        // ── Register Button ───────────────────────────────
        btnRegister.setOnClickListener(view -> handleRegister());

        // ── Toggle Mode ───────────────────────────────────
        tvToggle.setOnClickListener(view -> toggleMode());
    }

    // ── Login Logic ───────────────────────────────────────
    private void handleLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validation
        if (!v.isNotEmpty(etUsername, "Username")) return;
        if (!v.isNotEmpty(etPassword, "Password")) return;

        // DB Check
        long userId = db.validateUser(username, password);
        if (userId == -1) {
            v.showToast("Invalid username or password.");
            return;
        }

        // Navigate to Page 2, pass userId as extra
        navigateToPage2(userId, username);
    }

    // ── Register Logic ────────────────────────────────────
    private void handleRegister() {
        String username = etUsername.getText().toString().trim();
        String email    = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validation chain
        if (!v.isNotEmpty(etUsername, "Username")) return;
        if (!v.isNotEmpty(etEmail,    "Email"))    return;
        if (!v.isValidEmail(etEmail))              return;
        if (!v.isValidPassword(etPassword, 6))     return;

        // Check duplicate
        if (db.userExists(username)) {
            v.showToast("Username already taken. Choose another.");
            return;
        }

        // Insert
        long userId = db.insertUser(username, email, password);
        if (userId == -1) {
            v.showToast("Registration failed. Try again.");
            return;
        }

        v.showToast("Registered successfully! Welcome, " + username);
        navigateToPage2(userId, username);
    }

    // ── Navigate Forward ──────────────────────────────────
    private void navigateToPage2(long userId, String username) {
        Intent intent = new Intent(this, Page2_Input.class);
        intent.putExtra("USER_ID",   userId);
        intent.putExtra("USERNAME",  username);
        startActivity(intent);
    }

    // ── Toggle Login/Register UI ──────────────────────────
    private void toggleMode() {
        isLoginMode = !isLoginMode;
        if (isLoginMode) {
            if (tilEmail != null) tilEmail.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
            btnRegister.setVisibility(View.GONE);
            tvToggle.setText("Don't have an account? Register");
        } else {
            if (tilEmail != null) tilEmail.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.GONE);
            btnRegister.setVisibility(View.VISIBLE);
            tvToggle.setText("Already have an account? Login");
        }
    }
}