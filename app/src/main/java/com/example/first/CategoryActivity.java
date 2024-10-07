package com.example.first;
import android.util.Log;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CategoryActivity extends AppCompatActivity {

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
        final String[] categories = {"Crafts", "Jewelry", "Home Decor", "Fashion"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categories);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = categories[position];
                loadProductsByCategory(selectedCategory);
            }
        });
    }

    private void loadProductsByCategory(String category) {
        Cursor cursor = dbHelper.getProductsByCategory(category);

        if (cursor != null) {
            Log.d("CategoryActivity", "Cursor count: " + cursor.getCount()); // Log the cursor count
            if (cursor.getCount() > 0) {
                ProductAdapter adapter = new ProductAdapter(this, cursor, 0);
                listView.setAdapter(adapter);
            } else {
                Toast.makeText(this, "No products found in this category", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e("CategoryActivity", "Cursor is null"); // Log if the cursor is null
        }
    }


}
