package com.example.first;

import android.content.pm.PackageManager;
import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import android.content.SharedPreferences;
public class ProductDetailActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 101;
    private ImageView productImageView;
    private EditText productNameEditText, productDescriptionEditText, productPriceEditText, productCategoryEditText;
    private Uri imageUri;
    private DatabaseHelper dbHelper;  // Declare your DatabaseHelper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Initialize your DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        productImageView = findViewById(R.id.product_image);
        productNameEditText = findViewById(R.id.product_name);
        productDescriptionEditText = findViewById(R.id.product_description);
        productPriceEditText = findViewById(R.id.product_price);
        productCategoryEditText = findViewById(R.id.product_category);
        Button uploadProductButton = findViewById(R.id.btn_upload_product);

        productImageView.setOnClickListener(v -> openImagePicker());
        uploadProductButton.setOnClickListener(v -> uploadProduct());
    }

    private void openImagePicker() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is already granted, proceed to open the image picker
            pickImage();
        } else {
            // Request storage permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            // Take persistable URI permission to ensure you have access to the image
            final int takeFlags = data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getContentResolver().takePersistableUriPermission(imageUri, takeFlags);
            }

            loadImage();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed to pick the image
                pickImage();
            } else {
                Toast.makeText(this, "Permission denied to read storage", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadImage() {
        try {
            Bitmap bitmap;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                // Use ImageDecoder for API level 28 and above
                bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), imageUri));
            } else {
                // Fallback to MediaStore for lower API levels
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            }
            productImageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            Log.e("ProductDetailActivity", "Failed to load image", e);
            Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadProduct() {
        String productName = productNameEditText.getText().toString().trim();
        String productDescription = productDescriptionEditText.getText().toString().trim();
        String productPrice = productPriceEditText.getText().toString().trim();
        String productCategory = productCategoryEditText.getText().toString().trim();

        if (imageUri != null && !productName.isEmpty() && !productDescription.isEmpty() && !productPrice.isEmpty() && !productCategory.isEmpty()) {
            // Convert Uri to string for database storage
            String imageUriString = imageUri.toString();

            // Get seller ID from SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            int sellerId = sharedPreferences.getInt("SELLER_ID", -1); // Replace "SELLER_ID" with your key
            Toast.makeText(ProductDetailActivity.this, "Seller ID: " + sellerId, Toast.LENGTH_SHORT).show();

            // Save product details to the database including seller ID
            long result = dbHelper.addProduct(productName, productDescription, productPrice, productCategory, imageUriString, sellerId);
            if (result != -1) { // Check if insertion was successful
                Toast.makeText(this, "Product uploaded successfully!", Toast.LENGTH_SHORT).show();
                clearFields();
            } else {
                Toast.makeText(this, "Failed to upload product. Please try again.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please fill all details and select an image", Toast.LENGTH_SHORT).show();
        }
    }


    private void clearFields() {
        productNameEditText.setText("");
        productDescriptionEditText.setText("");
        productPriceEditText.setText("");
        productCategoryEditText.setText("");
        productImageView.setImageResource(0);
        imageUri = null;
    }
}
