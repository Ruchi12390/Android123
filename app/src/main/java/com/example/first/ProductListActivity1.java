package com.example.first;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ProductListActivity1 extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecyclerView recyclerViewProducts;
    private ProductAdapter1 productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list1);

        // Get the selected category from the intent
        Intent intent = getIntent();
        String selectedCategory = intent.getStringExtra("category");

        if (selectedCategory == null) {
            Toast.makeText(this, "No category selected", Toast.LENGTH_SHORT).show();
            return;
        }

        // Initialize RecyclerView and DatabaseHelper
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new DatabaseHelper(this);

        // Load products of the selected category
        loadProductsByCategory(selectedCategory);
    }

    private void loadProductsByCategory(String category) {
        List<Product> productList = new ArrayList<>();

        // Query database for products in the selected category
        Cursor cursor = dbHelper.getProductsByCategory(category);
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
            Toast.makeText(this, "No products found for this category", Toast.LENGTH_SHORT).show();
        } else {
            // Pass data to RecyclerView adapter
            productAdapter = new ProductAdapter1(this, productList);
            recyclerViewProducts.setAdapter(productAdapter);
        }
    }
}
