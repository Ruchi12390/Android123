package com.example.first;

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

        // Find the Login / Sign Up button
        Button loginSignupButton = findViewById(R.id.btn_login_signup);

        // Set an OnClickListener to show dialog
        loginSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoginDialog();
            }
        });
    }

    // Method to show the login options dialog
    private void showLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Login / Sign Up");
        builder.setMessage("Choose an option to continue:");

        // Add two buttons: Login as Seller and Login as Buyer
        builder.setPositiveButton("Login as Buyer", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Handle login as seller here
                Intent intent = new Intent(ProfileActivity.this, SellerLoginActivity.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Login as Seller", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Handle login as buyer here
                Intent intent = new Intent(ProfileActivity.this, BuyerLoginActivity.class);
                startActivity(intent);
            }
        });

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
