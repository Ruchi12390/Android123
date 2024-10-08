package com.example.first;

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

public class CategoryActivity1 extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        dbHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.listViewCategories);

        loadCategories();
    }

    private void loadCategories() {
        List<String> categories = new ArrayList<>();
        List<String> images = new ArrayList<>(); // For storing image URIs

        // Get distinct categories and their images from the database
        Cursor cursor = dbHelper.getDistinctCategoriesWithImages();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String category = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY));
                String imageUri = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE_URI));

                // Avoid duplicates in category list
                if (!categories.contains(category)) {
                    categories.add(category);
                    images.add(imageUri); // Store image URI associated with the category
                }
            }
            cursor.close(); // Close the cursor after use
        }

        // Check if categories were found
        if (categories.isEmpty()) {
            Toast.makeText(this, "No categories found", Toast.LENGTH_SHORT).show();
        } else {
            // Pass both categories and images to the adapter
            CategoryAdapter1 adapter = new CategoryAdapter1(this, categories, images);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Load products by selected category
                    String selectedCategory = categories.get(position * 2); // Get the first category of the row
                    loadProductsByCategory(selectedCategory);
                }
            });
        }
    }

    private void loadProductsByCategory(String category) {
        Cursor cursor = dbHelper.getProductsByCategory(category);

        if (cursor != null) {
            Log.d("CategoryActivity1", "Cursor count: " + cursor.getCount());
            if (cursor.getCount() > 0) {
                ProductAdapter adapter = new ProductAdapter(this, cursor, 0);
                listView.setAdapter(adapter);
            } else {
                Toast.makeText(this, "No products found in this category", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e("CategoryActivity1", "Cursor is null");
        }
    }
}
