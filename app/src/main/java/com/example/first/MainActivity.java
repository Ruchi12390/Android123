package com.example.first;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import android.view.View;

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecyclerView recyclerViewCategories;
    private RecyclerView recyclerViewProducts; // New RecyclerView for products

    // Image Slider Variables
    private ImageView imageViewSlider;
    private Button btnLeft, btnRight;
    private int[] imageArray = {R.drawable.img1, R.drawable.img2, R.drawable.img};
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Initialize RecyclerViews
        recyclerViewCategories = findViewById(R.id.recyclerViewCategories);
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts); // Initialize products RecyclerView

        // Set LayoutManager for category RecyclerView
        LinearLayoutManager categoryLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategories.setLayoutManager(categoryLayoutManager);

        // Set LayoutManager for products RecyclerView
        LinearLayoutManager productLayoutManager = new LinearLayoutManager(this);
        recyclerViewProducts.setLayoutManager(productLayoutManager);

        // Load categories into the RecyclerView
        loadCategories();

        // Load products into the RecyclerView
        loadProducts(); // Load products in addition to categories

        // Initialize Image Slider
        imageViewSlider = findViewById(R.id.imageViewSlider);
        btnLeft = findViewById(R.id.btn_left);
        btnRight = findViewById(R.id.btn_right);

        // Set initial image
        imageViewSlider.setImageResource(imageArray[currentIndex]);

        // Left arrow click listener
        btnLeft.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                imageViewSlider.setImageResource(imageArray[currentIndex]);
            }
        });

        // Right arrow click listener
        btnRight.setOnClickListener(v -> {
            if (currentIndex < imageArray.length - 1) {
                currentIndex++;
                imageViewSlider.setImageResource(imageArray[currentIndex]);
            }
        });

        // Bottom Navigation setup
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                Toast.makeText(MainActivity.this, "Home clicked", Toast.LENGTH_SHORT).show();
            } else if (item.getItemId() == R.id.nav_category) {
                Intent categoryIntent = new Intent(MainActivity.this, CategoryActivity.class);
                startActivity(categoryIntent);
            } else if (item.getItemId() == R.id.nav_profile) {
                Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(profileIntent);
            }
            return true;
        });

        // Setup "Select Image" button to open product detail activity
        Button selectImageButton = findViewById(R.id.btn_upload_image);
        selectImageButton.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Load categories and display them in the RecyclerView
    private void loadCategories() {
        List<String> categories = new ArrayList<>();
        List<String> images = new ArrayList<>();

        // Get distinct categories and their images from the database
        Cursor cursor = dbHelper.getDistinctCategoriesWithImages();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String category = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY));
                String imageUri = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE_URI));

                if (!categories.contains(category)) {
                    categories.add(category);
                    images.add(imageUri);
                }
            }
            cursor.close();
        }

        if (categories.isEmpty()) {
            Toast.makeText(this, "No categories found", Toast.LENGTH_SHORT).show();
        } else {
            // Pass data to RecyclerView adapter
            CategoryAdapter1 adapter = new CategoryAdapter1(this, categories, images);
            recyclerViewCategories.setAdapter(adapter);
        }

    }

    private void adjustRecyclerViewHeight(RecyclerView recyclerView) {
        int totalHeight = 0;
        for (int i = 0; i < recyclerView.getAdapter().getItemCount(); i++) {
            View view = recyclerView.getLayoutManager().findViewByPosition(i);
            if (view != null) {
                totalHeight += view.getHeight();
            }
        }
        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        params.height = totalHeight + (recyclerView.getHeight() * (recyclerView.getAdapter().getItemCount() - 1)); // Add margins
        recyclerView.setLayoutParams(params);
    }
    private void loadUserProfile() {
        // Navigate to SellerDashboardActivity
        Intent intent = new Intent(MainActivity.this, SellerDashboardActivity.class);
        startActivity(intent);
        finish(); // Optional: Call finish() to close ProfileActivity
    }

    // Load products and display them in the RecyclerView
    private void loadProducts() {
        List<Product> productList = new ArrayList<>();

        // Get all products from the database
        Cursor cursor = dbHelper.getAllProducts(); // Ensure you have this method in your DatabaseHelper
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
                String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION));
                String price = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRICE));
                String imageUri = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE_URI));

                Product product = new Product(name, description, price, imageUri);
                productList.add(product);
            }
            cursor.close();
        }

        if (productList.isEmpty()) {
            Toast.makeText(this, "No products found", Toast.LENGTH_SHORT).show();
        } else {
            // Pass data to RecyclerView adapter
            ProductAdapter1 adapter = new ProductAdapter1(this, productList);
            recyclerViewProducts.setAdapter(adapter);
        }
    }

}
