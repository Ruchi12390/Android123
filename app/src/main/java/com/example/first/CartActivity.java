package com.example.first;

import android.content.Intent; // Import Intent
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import android.widget.Toast;

public class CartActivity extends AppCompatActivity {

    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private ArrayList<Product1> cartItems;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Initialize RecyclerView
        cartRecyclerView = findViewById(R.id.cart_recycler_view);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get the buyer ID from the shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int buyerId = sharedPreferences.getInt("SELLER_ID", -1);
        int seller = sharedPreferences.getInt("BUYER_ID", -1);
        Toast.makeText(this, "Buyer ID: " +seller, Toast.LENGTH_SHORT).show();

        // Fetch cart items from the database for this buyer
        cartItems = databaseHelper.getCartItems(buyerId);

        // Log or Toast the size of the fetched items
        Toast.makeText(this, "Number of cart items: " + cartItems.size(), Toast.LENGTH_SHORT).show();

        // Initialize adapter with buyerId and databaseHelper
        cartAdapter = new CartAdapter(cartItems, buyerId, databaseHelper);
        cartRecyclerView.setAdapter(cartAdapter);
    }
}
