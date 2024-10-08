package com.example.first;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.load.engine.GlideException;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.DataSource;

public class ProductAdapter extends CursorAdapter {

    private static final String TAG = "ProductAdapter";

    public ProductAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_product, parent, false);
    }

    @Override




    public void bindView(View view, Context context, Cursor cursor) {
        Log.d("ProductAdapter", "bindView called");

        TextView textViewProduct = view.findViewById(R.id.textViewProduct);
        ImageView imageViewProduct = view.findViewById(R.id.imageViewProduct);

        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
        String imageUri = cursor.getString(cursor.getColumnIndexOrThrow("image_uri"));

        textViewProduct.setText(name + " - " + description);

        // Log the URI to ensure it's correctly saved
        Log.d("ProductAdapter", "Image URI: " + imageUri);

        // Load the image using Glide
        Glide.with(context)
                .load(Uri.parse(imageUri)) // Make sure the URI is correctly parsed
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.img) // Placeholder image if loading fails
                .into(imageViewProduct);
    }




}