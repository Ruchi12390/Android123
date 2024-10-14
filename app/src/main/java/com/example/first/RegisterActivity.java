package com.example.first;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private LinearLayout page1, page2, page3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_login); // Adjust as necessary

        // Initialize pages
        page1 = findViewById(R.id.page1);
        page2 = findViewById(R.id.page2);
        page3 = findViewById(R.id.page3);

        // Set up next button for page 1
        Button btnNextPage1 = findViewById(R.id.btn_next_page1);
        btnNextPage1.setOnClickListener(v -> {
            page1.setVisibility(View.GONE);
            page2.setVisibility(View.VISIBLE);
        });

        // Set up back button for page 2
        Button btnBackPage2 = findViewById(R.id.btn_back_page2);
        btnBackPage2.setOnClickListener(v -> {
            page2.setVisibility(View.GONE);
            page1.setVisibility(View.VISIBLE);
        });

        // Set up next button for page 2
        Button btnNextPage2 = findViewById(R.id.btn_next_page2);
        btnNextPage2.setOnClickListener(v -> {
            page2.setVisibility(View.GONE);
            page3.setVisibility(View.VISIBLE);
        });

        // Set up back button for page 3
        Button btnBackPage3 = findViewById(R.id.btn_back_page3);
        btnBackPage3.setOnClickListener(v -> {
            page3.setVisibility(View.GONE);
            page2.setVisibility(View.VISIBLE);
        });

        // Set up submit button for page 3
        Button btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(v -> {
            // Handle form submission here
            Toast.makeText(this, "Registration submitted!", Toast.LENGTH_SHORT).show();
        });
    }
}