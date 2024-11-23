package com.example.first;
import android.content.SharedPreferences;
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
                        int buyerId = databaseHelper.getBuyerId(email, password);
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("SELLER_ID", buyerId);
                        editor.putString("USER_ROLE", "SELLER_ID"); // Store role as SELLER_ID
                        editor.putBoolean("IS_LOGGED_IN", true); // Set login status
                        editor.apply(); // Save the changes
                        // Navigate to Seller Dashboa

                        Toast.makeText(SellerLogin1.this, "Seller ID: " + buyerId, Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(SellerLogin1.this, SellerDashboardActivity1.class);
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
                Intent intent = new Intent(SellerLogin1.this, SellerSign.class);
                startActivity(intent);
            }
        });
    }
}
