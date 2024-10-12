package com.example.first;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class BuyerDashboardActivity extends AppCompatActivity {

    private TextView welcomeTextView; // TextView to display a welcome message or buyer-related info

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_dashboard);

        // Initialize the TextView
        welcomeTextView = findViewById(R.id.buyer_welcome_text);

        // Get the buyer ID passed from the SellerLoginActivity
        int buyerId = getIntent().getIntExtra("BUYER_ID", -1);

        // Check if buyerId is valid
        if (buyerId != -1) {
            // Display the buyer ID or load buyer-specific information here
            welcomeTextView.setText("Welcome, Buyer #" + buyerId);
        } else {
            // If buyerId is invalid, show a message and return to login screen
            Toast.makeText(this, "Error: Unable to retrieve buyer information.", Toast.LENGTH_SHORT).show();

            // Optionally, navigate back to login activity if needed
            Intent intent = new Intent(BuyerDashboardActivity.this, SellerLoginActivity.class);
            startActivity(intent);
            finish(); // End this activity to prevent user from returning to it
        }
    }
}
