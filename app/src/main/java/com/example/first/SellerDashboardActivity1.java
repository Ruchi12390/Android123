package com.example.first;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SellerDashboardActivity1 extends AppCompatActivity {

    private Button uploadImageButton, logoutButton, yourOrdersButton;
    private TextView welcomeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_dashboard);

        uploadImageButton = findViewById(R.id.btn_upload_image);
        logoutButton = findViewById(R.id.btn_logout); // Reference Logout button
        welcomeMessage = findViewById(R.id.welcome_message);
        yourOrdersButton = findViewById(R.id.btn_your_orders); // New button for orders

        // Retrieve seller details from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int sellerId = sharedPreferences.getInt("SELLER_ID", -1);
        String userRole = sharedPreferences.getString("USER_ROLE", "");

        Toast.makeText(this, "Seller ID: " + sellerId + ", Role: " + userRole, Toast.LENGTH_SHORT).show();

        if (sellerId != -1 && "SELLER_ID".equals(userRole)) {
            welcomeMessage.setText("Welcome, Seller " + sellerId);
        } else {
            Toast.makeText(this, "Please log in as a seller", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Handle "Upload Image" button click
        uploadImageButton.setOnClickListener(v -> {
            // Navigate to the activity for uploading images
            Intent intent = new Intent(SellerDashboardActivity1.this, ProductDetailActivity.class);
            startActivity(intent);
        });

        // Handle "Your Orders" button click
        yourOrdersButton.setOnClickListener(v -> {
            // Fetch orders for the seller and pass to another activity
            fetchOrdersForSeller(sellerId);
        });

        // Logout functionality
        logoutButton.setOnClickListener(v -> {
            // Clear SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            // Navigate back to login activity
            Intent intent = new Intent(SellerDashboardActivity1.this, SellerLogin1.class);
            startActivity(intent);
            finish(); // Close the dashboard activity
        });
    }

    private void fetchOrdersForSeller(int sellerId) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query to fetch orders for the logged-in seller
        String query = "SELECT " + DatabaseHelper.ORDER_ID + ", " + DatabaseHelper.ORDER_PRODUCT_NAME +
                " FROM " + DatabaseHelper.TABLE_ORDERS +
                " WHERE " + DatabaseHelper.ORDER_SELLER_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(sellerId)});

        if (cursor.moveToFirst()) {
            StringBuilder ordersList = new StringBuilder();

            // Loop through the orders and add to a list (can be used in a ListView or RecyclerView)
            do {
                int orderId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ORDER_ID));
                String productName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ORDER_PRODUCT_NAME));
                ordersList.append("Order ID: ").append(orderId)
                        .append(", Product: ").append(productName).append("\n");
            } while (cursor.moveToNext());

            // Show the orders in a Toast (you can replace this with a new activity or list view)
            Toast.makeText(this, ordersList.toString(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "No orders found for this seller", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        db.close();
    }
}
