package com.example.first;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

public class SellerDashboardActivity extends AppCompatActivity {

    private Button ordersButton, wishlistButton, managePasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_dashboard_activity);

        // Initialize buttons
        ordersButton = findViewById(R.id.orders_button);
        wishlistButton = findViewById(R.id.wishlist_button);
        managePasswordButton = findViewById(R.id.manage_password_button);

        // Set onClick listeners for the buttons
        ordersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Placeholder for orders functionality
                Toast.makeText(SellerDashboardActivity.this, "Your Orders clicked", Toast.LENGTH_SHORT).show();
                // You can navigate to OrdersActivity here
                // Intent intent = new Intent(SellerDashboardActivity.this, OrdersActivity.class);
                // startActivity(intent);
            }
        });

        wishlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Placeholder for wishlist functionality
                Toast.makeText(SellerDashboardActivity.this, "Wishlist clicked", Toast.LENGTH_SHORT).show();
                // You can navigate to WishlistActivity here
                // Intent intent = new Intent(SellerDashboardActivity.this, WishlistActivity.class);
                // startActivity(intent);
            }
        });

        managePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Placeholder for manage password functionality
                Toast.makeText(SellerDashboardActivity.this, "Manage Password clicked", Toast.LENGTH_SHORT).show();
                // You can navigate to ManagePasswordActivity here
                // Intent intent = new Intent(SellerDashboardActivity.this, ManagePasswordActivity.class);
                // startActivity(intent);
            }
        });
    }
}
