package com.example.first;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;
import android.content.SharedPreferences;

public class SellerDashboardActivity extends AppCompatActivity {
    private int sellerId;
    private Button ordersButton, wishlistButton, managePasswordButton, cartButton, logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_dashboard_activity);

        // Get sellerId from Intent or SharedPreferences
        sellerId = getIntent().getIntExtra("SELLER_ID", -1);

        // Initialize buttons
        ordersButton = findViewById(R.id.orders_button);
        wishlistButton = findViewById(R.id.wishlist_button);
        managePasswordButton = findViewById(R.id.manage_password_button);
        cartButton = findViewById(R.id.cart_button);
        logoutButton = findViewById(R.id.logout_button);  // Initialize logout button

        // Set onClick listeners for other buttons
        ordersButton.setOnClickListener(v ->
                Toast.makeText(SellerDashboardActivity.this, "Your Orders clicked", Toast.LENGTH_SHORT).show());

        wishlistButton.setOnClickListener(v -> {
            Intent intent = new Intent(SellerDashboardActivity.this, WishlistActivity.class);
            startActivity(intent);
        });

        managePasswordButton.setOnClickListener(v ->
                Toast.makeText(SellerDashboardActivity.this, "Manage Password clicked", Toast.LENGTH_SHORT).show());

        cartButton.setOnClickListener(v -> {
            Toast.makeText(SellerDashboardActivity.this, "View Cart clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SellerDashboardActivity.this, CartActivity.class);
            startActivity(intent);
        });

        // Set onClick listener for logout button
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("BUYER_ID");  // Remove the sellerId from SharedPreferences
                editor.remove("USER_ROLE");  // Optionally remove the user role
                editor.putBoolean("IS_LOGGED_IN", false); // Set login status to false
                editor.apply();  // Commit changes

                // Show logout message
                Toast.makeText(SellerDashboardActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();

                // Redirect to ProfileActivity (or login activity)
                Intent intent = new Intent(SellerDashboardActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();  // Close the SellerDashboardActivity
            }
        });
    }
}
