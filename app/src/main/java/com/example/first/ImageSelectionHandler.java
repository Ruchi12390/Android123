package com.example.first;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

public class ImageSelectionHandler {

    private final Context context;

    public ImageSelectionHandler(Context context) {
        this.context = context;
    }

    public void setupSelectImageButton(Button selectImageButton) {
        selectImageButton.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
