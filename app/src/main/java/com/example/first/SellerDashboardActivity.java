package com.example.first;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

public class SellerDashboardActivity extends AppCompatActivity {
    private int sellerId;
    private Button ordersButton, wishlistButton, managePasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_dashboard_activity);
        sellerId = getIntent().getIntExtra("BUYER_ID", -1);
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

        // Add the wishlistButton click functionality
        wishlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Placeholder for wishlist functionality
                Toast.makeText(SellerDashboardActivity.this, "Wishlist clicked", Toast.LENGTH_SHORT).show();
                // You can navigate to WishlistActivity here
                Intent intent = new Intent(SellerDashboardActivity.this, WishlistActivity.class);
                intent.putExtra("SELLER_ID", sellerId);
                startActivity(intent);
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
