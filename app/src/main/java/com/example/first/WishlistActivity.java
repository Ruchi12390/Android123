package com.example.first;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import android.widget.Toast;
public class WishlistActivity extends AppCompatActivity {

    private RecyclerView wishlistRecyclerView;
    private WishlistAdapter wishlistAdapter;
    private ArrayList<Product> wishlistItems;
    private DatabaseHelper databaseHelper; // Declare DatabaseHelper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Initialize RecyclerView
        wishlistRecyclerView = findViewById(R.id.wishlist_recycler_view);
        wishlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get the buyer ID from intent
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int buyerId = sharedPreferences.getInt("BUYER_ID", -1);
        Toast.makeText(this, "Buyer ID: " + buyerId, Toast.LENGTH_SHORT).show();

        // Fetch wishlist items from the database for this buyer
        // Fetch wishlist items from the database for this buyer
        wishlistItems = databaseHelper.getWishlistItems(buyerId);

// Log or Toast the size of the fetched items
        Toast.makeText(this, "Number of wishlist items: " + wishlistItems.size(), Toast.LENGTH_SHORT).show();

        // Initialize adapter
        wishlistAdapter = new WishlistAdapter(wishlistItems);
        wishlistRecyclerView.setAdapter(wishlistAdapter);
    }
}
