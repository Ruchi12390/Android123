package com.example.first;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;

public class SellerLoginActivity1 extends AppCompatActivity {

    private EditText sellerEmail, sellerPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);

        // Initialize views
        sellerEmail = findViewById(R.id.seller_email);
        sellerPassword = findViewById(R.id.seller_password);
        Button loginButton = findViewById(R.id.seller_login_button);

        // Set OnClickListener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = sellerEmail.getText().toString().trim();
                String password = sellerPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SellerLoginActivity1.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                } else {
                    // Code to validate login credentials (e.g., check against database)
                    // Example: boolean isValidUser = databaseHelper.validateSeller(email, password);

                    // Assuming the login is valid
                    // Save login status and user role in SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("IS_LOGGED_IN", true);
                    editor.putString("USER_ROLE", "SELLER_ID"); // Replace with actual user role if needed
                    editor.apply();

                    Toast.makeText(SellerLoginActivity1.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    // Redirect to SellerDashboardActivity
                    Intent intent = new Intent(SellerLoginActivity1.this, SellerDashboardActivity.class);
                    startActivity(intent);
                    finish(); // Optional: Call finish() to close SellerLoginActivity
                }
            }
        });

        // Set OnClickListener for signup option
        TextView signupOption = findViewById(R.id.seller_signup_option);
        signupOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to the signup activity
                Intent intent = new Intent(SellerLoginActivity1.this, SellerSignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}

