package com.example.first;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SellerSignUpActivity extends AppCompatActivity {

    private EditText sellerNameInput, sellerEmailInput, sellerPhoneInput, sellerAddressInput, sellerPasswordInput;
    private EditText sellerUpiInput, sellerBankAccountInput, sellerIfscInput, sellerGstInput;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_sign_up);

        // Initialize inputs
        sellerNameInput = findViewById(R.id.seller_name_input);
        sellerEmailInput = findViewById(R.id.seller_email_input);
        sellerPhoneInput = findViewById(R.id.seller_phone_input);
        sellerAddressInput = findViewById(R.id.seller_address_input);
        sellerPasswordInput = findViewById(R.id.seller_password_input);
        sellerUpiInput = findViewById(R.id.seller_upi_input);
        sellerBankAccountInput = findViewById(R.id.seller_bank_account_input);
        sellerIfscInput = findViewById(R.id.seller_ifsc_input);
        sellerGstInput = findViewById(R.id.seller_gst_input);
        signUpButton = findViewById(R.id.signup_button);

        // Set sign-up button click listener
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get values from inputs
                String name = sellerNameInput.getText().toString().trim();
                String email = sellerEmailInput.getText().toString().trim();
                String phone = sellerPhoneInput.getText().toString().trim();
                String address = sellerAddressInput.getText().toString().trim();
                String password = sellerPasswordInput.getText().toString().trim();
                String upiId = sellerUpiInput.getText().toString().trim();
                String bankAccount = sellerBankAccountInput.getText().toString().trim();
                String ifscCode = sellerIfscInput.getText().toString().trim();
                String gstNumber = sellerGstInput.getText().toString().trim();

                // Validate inputs
                if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() ||
                        password.isEmpty() || upiId.isEmpty() || bankAccount.isEmpty() || ifscCode.isEmpty()) {
                    Toast.makeText(SellerSignUpActivity.this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Create a Buyer object
                    Buyer buyer = new Buyer(name, email, phone, address, password, upiId, bankAccount, ifscCode, gstNumber);

                    // Save the buyer information to the database
                    DatabaseHelper dbHelper = new DatabaseHelper(SellerSignUpActivity.this);
                    dbHelper.addBuyer(buyer);

                    Toast.makeText(SellerSignUpActivity.this, "Seller registered successfully!", Toast.LENGTH_SHORT).show();
                    // Navigate back or to another activity
                    finish(); // Close current activity
                }
            }
        });

    }
}
