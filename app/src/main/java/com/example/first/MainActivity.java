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

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecyclerView recyclerViewCategories;

    // Image Slider Variables
    private ImageView imageViewSlider;
    private Button btnLeft, btnRight;
    private int[] imageArray = {R.drawable.img1, R.drawable.img2, R.drawable.img};
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView and DatabaseHelper
        dbHelper = new DatabaseHelper(this);
        recyclerViewCategories = findViewById(R.id.recyclerViewCategories);

        // Set LayoutManager for horizontal scrolling
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategories.setLayoutManager(layoutManager);

        // Load categories into the RecyclerView
        loadCategories();

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
                // Optionally navigate to HomeActivity if needed
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
}
