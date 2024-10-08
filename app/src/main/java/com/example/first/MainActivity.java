package com.example.first;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;



import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ListView listViewCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize ListView and DatabaseHelper
        dbHelper = new DatabaseHelper(this);
        listViewCategories = findViewById(R.id.listViewCategories);

        // Load categories into the ListView
        loadCategories();

        // Bottom Navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                Toast.makeText(MainActivity.this, "Home clicked", Toast.LENGTH_SHORT).show();
            } else if (item.getItemId() == R.id.nav_category) {
                Intent categoryIntent = new Intent(MainActivity.this, CategoryActivity.class);
                startActivity(categoryIntent);
            } else if (item.getItemId() == R.id.nav_profile) {
                Toast.makeText(MainActivity.this, "Profile clicked", Toast.LENGTH_SHORT).show();
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

    // Load categories and display them in the ListView
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
            CategoryAdapter adapter = new CategoryAdapter(this, categories, images);
            listViewCategories.setAdapter(adapter);

            listViewCategories.setOnItemClickListener((parent, view, position, id) -> {
                String selectedCategory = categories.get(position * 2); // Get the first category of the row
                loadProductsByCategory(selectedCategory);
            });
        }
    }

    // Load products by selected category
    private void loadProductsByCategory(String category) {
        Cursor cursor = dbHelper.getProductsByCategory(category);

        if (cursor != null) {
            Log.d("MainActivity", "Cursor count: " + cursor.getCount());
            if (cursor.getCount() > 0) {
                ProductAdapter adapter = new ProductAdapter(this, cursor, 0);
                listViewCategories.setAdapter(adapter);
            } else {
                Toast.makeText(this, "No products found in this category", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e("MainActivity", "Cursor is null");
        }
    }
}
