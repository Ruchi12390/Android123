package com.example.first;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PlaceOrderActivity extends AppCompatActivity {

    private EditText editName, editAddress, editLocality, editDistrict, editPincode, editMobile;
    private TextView txtAmount; // TextView to display the amount
    private Button btnPayNow;

    private int productId; // Product ID passed via Intent
    private String buyerId; // Buyer ID fetched from the database
    private int sellerId; // Seller ID fetched from SharedPreferences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        // Initialize views
        editName = findViewById(R.id.edit_name);
        editAddress = findViewById(R.id.edit_address);
        editLocality = findViewById(R.id.edit_locality);
        editDistrict = findViewById(R.id.edit_district);
        editPincode = findViewById(R.id.edit_pincode);
        editMobile = findViewById(R.id.edit_mobile);
        txtAmount = findViewById(R.id.txt_amount);
        btnPayNow = findViewById(R.id.btn_pay_now);

        // Get the product price from the Intent
        double productPrice = getIntent().getDoubleExtra("product_price", -1.0);
        if (productPrice != -1.0) {
            txtAmount.setText("Amount: ₹" + productPrice);
        } else {
            Toast.makeText(this, "Failed to retrieve product price", Toast.LENGTH_SHORT).show();
        }

        // Get product ID from the Intent
        productId = getIntent().getIntExtra("product_id", -1);
        if (productId == -1) {
            Toast.makeText(this, "Product ID is missing", Toast.LENGTH_SHORT).show();
            return;
        }

        // Fetch SELLER_ID from SharedPreferences
        sellerId = getSellerIdFromPreferences();
        if (sellerId == -1) {
            Toast.makeText(this, "Failed to fetch Seller ID", Toast.LENGTH_SHORT).show();
            return;
        }

        // Fetch the BUYER_ID from the products table
        fetchBuyerIdFromProductsTable();

        // Set up the "Pay Now" button click listener
        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePlaceOrder();
            }
        });
    }

    private int getSellerIdFromPreferences() {
        // Fetch SELLER_ID from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int seller = sharedPreferences.getInt("BUYER_ID", -1);
        Toast.makeText(this, "Seller id in oder: " +seller, Toast.LENGTH_SHORT).show();
        return  seller;
    }

    private String productName; // Variable to store the product name

    private void fetchBuyerIdFromProductsTable() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query to fetch buyer_id and product_name for the given product_id
        Cursor cursor = db.rawQuery("SELECT " + DatabaseHelper.BUYER_ID + ", " + DatabaseHelper.COLUMN_NAME + " FROM " +
                        DatabaseHelper.TABLE_PRODUCTS + " WHERE " + DatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(productId)});

        if (cursor.moveToFirst()) {
            buyerId = cursor.getString(cursor.getColumnIndex(DatabaseHelper.BUYER_ID));
            productName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)); // Fetch product name
        }
        cursor.close();
        db.close();

        if (buyerId == null || productName == null) {
            Toast.makeText(this, "Failed to fetch Buyer ID or Product Name", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Buyer ID: " + buyerId + ", Product Name: " + productName, Toast.LENGTH_SHORT).show();
        }
    }


    private void handlePlaceOrder() {
        // Retrieve input values
        String name = editName.getText().toString();
        String address = editAddress.getText().toString();
        String locality = editLocality.getText().toString();
        String district = editDistrict.getText().toString();
        String pincode = editPincode.getText().toString();
        String mobile = editMobile.getText().toString();

        // Validate inputs
        if (name.isEmpty() || address.isEmpty() || locality.isEmpty() ||
                district.isEmpty() || pincode.isEmpty() || mobile.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insert order data into the "orders" table
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Use the actual productName retrieved from the database
        String insertOrderQuery = "INSERT INTO orders (buyer_id, seller_id, product_id, product_name) VALUES (?, ?, ?, ?)";
        db.execSQL(insertOrderQuery, new Object[]{buyerId, sellerId, productId, productName}); // Use the actual product name

        Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show();

        // Close the database
        db.close();
    }

}
