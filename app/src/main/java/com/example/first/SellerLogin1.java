package com.example.first;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SellerLogin1 extends AppCompatActivity {

    private EditText sellerEmailInput, sellerPasswordInput;
    private Button loginButton;
    private TextView signUpOption;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login1);

        sellerEmailInput = findViewById(R.id.seller_email_input);
        sellerPasswordInput = findViewById(R.id.seller_password_input);
        loginButton = findViewById(R.id.login_button);
        signUpOption = findViewById(R.id.signup_option);
        databaseHelper = new DatabaseHelper(this); // Initialize DatabaseHelper

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = sellerEmailInput.getText().toString().trim();
                String password = sellerPasswordInput.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SellerLogin1.this, "Please enter your email and password", Toast.LENGTH_SHORT).show();
                } else {
                    // Call login method
                    boolean loginSuccess = databaseHelper.loginBuyer(email, password);
                    if (loginSuccess) {
                        // Navigate to Seller Dashboard
                        Intent intent = new Intent(SellerLogin1.this, SellerDashboardActivity.class);
                        startActivity(intent);
                        finish(); // Optional: close the login activity
                    } else {
                        Toast.makeText(SellerLogin1.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signUpOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Sign Up page
                Intent intent = new Intent(SellerLogin1.this, SellerSignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
