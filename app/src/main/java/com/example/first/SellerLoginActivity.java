package com.example.first;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SellerLoginActivity extends AppCompatActivity {

    private EditText sellerEmailInput, sellerPasswordInput;
    private TextView signUpOption; // TextView for "Sign Up"
    private DatabaseHelper databaseHelper; // Declare DatabaseHelper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Initialize views
        sellerEmailInput = findViewById(R.id.seller_email);
        sellerPasswordInput = findViewById(R.id.seller_password);
        Button loginButton = findViewById(R.id.seller_login_button);
        signUpOption = findViewById(R.id.seller_signup_option);  // Sign Up TextView

        // Set OnClickListener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = sellerEmailInput.getText().toString().trim();
                String password = sellerPasswordInput.getText().toString().trim();

                // Validate login
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SellerLoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                } else {
                    // Check login in the database
                    if (databaseHelper.checkSellerLogin(email, password)) {
                        // Navigate to Seller Dashboard on successful login
                        Intent intent = new Intent(SellerLoginActivity.this, SellerDashboardActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SellerLoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Set OnClickListener for sign-up option to navigate to Sign-Up activity
        signUpOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SellerSignUpActivity
                Intent intent = new Intent(SellerLoginActivity.this, SellerSignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
