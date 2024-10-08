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

public class CategoryActivity1 extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecyclerView recyclerViewCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category1);

        dbHelper = new DatabaseHelper(this);
        recyclerViewCategories = findViewById(R.id.recyclerViewCategories);

        // Set LayoutManager for horizontal scrolling
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategories.setLayoutManager(layoutManager);

        // Load categories into the RecyclerView
        loadCategories();
    }

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
