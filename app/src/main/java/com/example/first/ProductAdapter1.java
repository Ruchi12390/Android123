package com.example.first;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProductAdapter1 extends RecyclerView.Adapter<ProductAdapter1.ProductViewHolder> {

    private Context context;
    private List<Product> productList;
    private DatabaseHelper databaseHelper; // Helper class for database operations

    public ProductAdapter1(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
        databaseHelper = new DatabaseHelper(context); // Initialize DatabaseHelper
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override


    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.textViewName.setText(product.getName());
        holder.textViewDescription.setText(product.getDescription());
        holder.textViewPrice.setText(product.getPrice());

        // Load product image using Glide
        Glide.with(context)
                .load(product.getImageUri()) // Ensure this is a valid image URI or URL
                .placeholder(R.drawable.img) // Optional placeholder image
                .error(R.drawable.img2) // Optional error image
                .into(holder.imageViewProduct);

        // Check if the product is already in the wishlist and update button text accordingly
        int currentSellerId = getCurrentSellerId();
        if (databaseHelper.isProductInWishlist(currentSellerId, databaseHelper.getProductIdByName(product.getName()))) {
            holder.addToWishlistButton.setText("Remove from Wishlist");
        } else {
            holder.addToWishlistButton.setText("Add to Wishlist");
        }

        // Check if the product is already in the cart and update button text accordingly
        if (databaseHelper.isProductInCart(currentSellerId, databaseHelper.getProductIdByName(product.getName()))) {
            holder.addToCartButton.setText("Remove from Cart");
        } else {
            holder.addToCartButton.setText("Add to Cart");
        }

        // Set up button listeners for "Add to Wishlist" and "Add to Cart/Remove from Cart"
        holder.addToWishlistButton.setOnClickListener(v -> {
            addToWishlist(product, holder); // Pass the holder to update the button text
        });

        holder.addToCartButton.setOnClickListener(v -> {
            toggleCartStatus(product, holder); // Handle add/remove from cart logic
        });

        // Optional: Set an OnClickListener for item clicks (e.g., open product detail activity)
        holder.itemView.setOnClickListener(v -> {
            // Handle item click here
        });
    }

    // Method to add or remove a product from the cart
    private void toggleCartStatus(Product product, ProductViewHolder holder) {
        int currentSellerId = getCurrentSellerId(); // Retrieve current seller ID from shared preferences
        int productId = databaseHelper.getProductIdByName(product.getName());

        // Check if the product is already in the cart
        if (databaseHelper.isProductInCart(currentSellerId, productId)) {
            // If the product is in the cart, remove it
            long result = databaseHelper.removeProductFromCart(currentSellerId, productId);
            if (result != -1) {
                Toast.makeText(context, "Removed from Cart", Toast.LENGTH_SHORT).show();
                holder.addToCartButton.setText("Add to Cart"); // Update button text
            } else {
                Toast.makeText(context, "Failed to remove from Cart", Toast.LENGTH_SHORT).show();
            }
        } else {
            // If the product is not in the cart, add it
            long result = databaseHelper.addProductToCart(currentSellerId, productId);
            if (result != -1) {
                Toast.makeText(context, "Added to Cart", Toast.LENGTH_SHORT).show();
                holder.addToCartButton.setText("Remove from Cart"); // Update button text
            } else {
                Toast.makeText(context, "Failed to add to Cart", Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0; // Safeguard against null list
    }

    // Method to add a product to the wishlist
    private void addToWishlist(Product product, ProductViewHolder holder) {
        int currentSellerId = getCurrentSellerId(); // Retrieve current seller ID from shared preferences
        int productId = databaseHelper.getProductIdByName(product.getName());

        // Check if the product is already in the wishlist
        if (databaseHelper.isProductInWishlist(currentSellerId, productId)) {
            // If the product is in the wishlist, remove it
            long result = databaseHelper.removeProductFromWishlist(currentSellerId, productId);
            if (result != -1) {
                Toast.makeText(context, "Removed from Wishlist", Toast.LENGTH_SHORT).show();
                holder.addToWishlistButton.setText("Add to Wishlist"); // Update button text
            } else {
                Toast.makeText(context, "Failed to remove from Wishlist", Toast.LENGTH_SHORT).show();
            }
        } else {
            // If the product is not in the wishlist, add it
            long result = databaseHelper.addProductToWishlist(currentSellerId, productId);
            if (result != -1) {
                Toast.makeText(context, "Added to Wishlist", Toast.LENGTH_SHORT).show();
                holder.addToWishlistButton.setText("Remove from Wishlist"); // Update button text
            } else {
                Toast.makeText(context, "Failed to add to Wishlist", Toast.LENGTH_SHORT).show();
            }
        }
    }



    // Method to add a product to the cart
    private void addToCart(Product product) {
        int currentSellerId = getCurrentSellerId(); // Retrieve current seller ID from shared preferences

        long result = databaseHelper.addProductToCart(currentSellerId, databaseHelper.getProductIdByName(product.getName())); // Correct method call

        if (result != -1) {
            Toast.makeText(context, "Added to Cart", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Failed to add to Cart", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to get current seller ID
    private int getCurrentSellerId() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE); // Use the context
        return sharedPreferences.getInt("SELLER_ID", -1); // Default value is -1 if not found
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewDescription;
        TextView textViewPrice;
        ImageView imageViewProduct;
        Button addToWishlistButton;
        Button addToCartButton;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            addToWishlistButton = itemView.findViewById(R.id.btn_add_wishlist);
            addToCartButton = itemView.findViewById(R.id.btn_add_cart);
        }
    }
}
