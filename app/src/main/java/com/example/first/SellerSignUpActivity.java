package com.example.first;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SellerSignUpActivity extends AppCompatActivity {

    private EditText sellerNameInput, sellerEmailInput, sellerPasswordInput;
    private Button signUpButton;
    private DatabaseHelper databaseHelper; // Initialize DatabaseHelper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_signup);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Initialize views
        sellerNameInput = findViewById(R.id.seller_name);
        sellerEmailInput = findViewById(R.id.seller_email);
        sellerPasswordInput = findViewById(R.id.seller_password);
        signUpButton = findViewById(R.id.seller_signup_button);

        // Set OnClickListener for sign-up button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = sellerNameInput.getText().toString().trim();
                String email = sellerEmailInput.getText().toString().trim();
                String password = sellerPasswordInput.getText().toString().trim();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SellerSignUpActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Add the new seller to the database
                    long result = databaseHelper.addSeller(email, password);
                    if (result != -1) {
                        Toast.makeText(SellerSignUpActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                        // Navigate back to login or dashboard
                        finish();
                    } else {
                        Toast.makeText(SellerSignUpActivity.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
