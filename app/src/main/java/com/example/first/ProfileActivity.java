package com.example.first;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int sellerId = sharedPreferences.getInt("BUYER_ID", -1);
        Toast.makeText(ProfileActivity.this, "BUYER ID: " + sellerId, Toast.LENGTH_SHORT).show();

        // Check if the user is already logged in
        if (sellerId==-1) {
            // Find the Login / Sign Up button
            Button loginSignupButton = findViewById(R.id.btn_login_signup);

            // Set an OnClickListener to show dialog
            loginSignupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showLoginDialog();
                }
            });
        } else {
            // User is already logged in, proceed with loading the profile
            loadUserProfile(); // Navigate to SellerDashboardActivity
        }
    }

    // Method to check if the user is logged in
    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return sharedPreferences.getBoolean("IS_LOGGED_IN", false); // Replace "IS_LOGGED_IN" with your key
    }

    // Method to show the login options dialog
    private void showLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Login / Sign Up");
        builder.setMessage("Choose an option to continue:");

        // Buyer button should open BuyerLoginActivity
        builder.setPositiveButton("Login as Buyer", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Open BuyerLoginActivity
                Intent intent = new Intent(ProfileActivity.this, SellerLogin1.class);
                startActivity(intent);
            }
        });

        // Seller button should open SellerLoginActivity
        builder.setNegativeButton("Login as Seller", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Open SellerLoginActivity
                Intent intent = new Intent(ProfileActivity.this, SellerLoginActivity.class);
                startActivity(intent);
            }
        });

        // Optional: Add a cancel button
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Close the dialog
            }
        });

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Method to load user profile and navigate to SellerDashboardActivity
    private void loadUserProfile() {
        // Navigate to SellerDashboardActivity
        Intent intent = new Intent(ProfileActivity.this, SellerDashboardActivity.class);
        startActivity(intent);
        finish(); // Optional: Call finish() to close ProfileActivity
    }
}