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
import android.widget.GridView;

public class CategoryActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        dbHelper = new DatabaseHelper(this);
        gridView = findViewById(R.id.gridViewCategories);

        loadCategories();
    }

    private void loadCategories() {
        List<String> categories = new ArrayList<>();
        List<String> images = new ArrayList<>();

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
            gridView.setAdapter(adapter);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedCategory = categories.get(position);
                    loadProductsByCategory(selectedCategory);
                }
            });
        }
    }

    private void loadProductsByCategory(String category) {
        Cursor cursor = dbHelper.getProductsByCategory(category);

        if (cursor != null && cursor.getCount() > 0) {
            ProductAdapter adapter = new ProductAdapter(this, cursor, 0);
            gridView.setAdapter(adapter);  // Display products in the same GridView
        } else {
            Toast.makeText(this, "No products found in this category", Toast.LENGTH_SHORT).show();
        }
    }
}
