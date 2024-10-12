package com.example.first;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide; // Import Glide

import java.util.ArrayList;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder> {

    private ArrayList<Product> wishlistItems;

    public WishlistAdapter(ArrayList<Product> wishlistItems) {
        this.wishlistItems = wishlistItems;
    }

    @NonNull
    @Override
    public WishlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item, parent, false);
        return new WishlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistViewHolder holder, int position) {
        Product product = wishlistItems.get(position);
        holder.productNameTextView.setText(product.getName());
        holder.productDescriptionTextView.setText(product.getDescription());
        holder.productPriceTextView.setText("$" + product.getPrice());

        // Load product image using Glide
        Glide.with(holder.itemView.getContext())
                .load(product.getImageUri()) // Ensure this returns a valid image URI or URL
                .placeholder(R.drawable.img) // Optional placeholder
                .error(R.drawable.img) // Optional error image
                .into(holder.productImageView); // Set the image into the ImageView
    }

    @Override
    public int getItemCount() {
        return wishlistItems.size();
    }

    public static class WishlistViewHolder extends RecyclerView.ViewHolder {

        TextView productNameTextView, productDescriptionTextView, productPriceTextView;
        ImageView productImageView; // ImageView for the product image

        public WishlistViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.wishlist_product_image); // Initialize the ImageView
            productNameTextView = itemView.findViewById(R.id.wishlist_product_name);
            productDescriptionTextView = itemView.findViewById(R.id.wishlist_product_description);
            productPriceTextView = itemView.findViewById(R.id.wishlist_product_price);
        }
    }
}
