package com.example.first;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button; // Import Button
import android.widget.ImageButton; // Import ImageButton
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private ArrayList<Product> cartItems;
    private int buyerId; // Add buyerId to associate with the product removal
    private DatabaseHelper databaseHelper; // Database helper reference

    public CartAdapter(ArrayList<Product> cartItems, int buyerId, DatabaseHelper dbHelper) {
        this.cartItems = cartItems;
        this.buyerId = buyerId;
        this.databaseHelper = dbHelper; // Initialize the database helper
    }
    public void removeItem(int position) {
        if (position >= 0 && position < cartItems.size()) {
            cartItems.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItems.size()); // Notify the adapter of the change
        }
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override



    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = cartItems.get(position);
        holder.productNameTextView.setText(product.getName());
        holder.productDescriptionTextView.setText(product.getDescription());
        holder.productPriceTextView.setText("$" + product.getPrice());

        // Load product image using Glide
        Glide.with(holder.itemView.getContext())
                .load(product.getImageUri())
                .placeholder(R.drawable.img)
                .error(R.drawable.img)
                .into(holder.productImageView);

        // Set the remove button click listener
        holder.itemView.findViewById(R.id.btn_remove).setOnClickListener(v -> {
            long result = databaseHelper.removeProductFromCart1(buyerId, product.getName());
            if (result > 0) {
                removeItem(position); // Remove item from adapter
            } else {
                Toast.makeText(holder.itemView.getContext(), "Error removing item", Toast.LENGTH_SHORT).show();
            }
        });
    }




    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        TextView productNameTextView, productDescriptionTextView, productPriceTextView;
        ImageView productImageView;
        Button btnRemove; // Change to Button or keep ImageButton based on your design

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.cart_product_image);
            productNameTextView = itemView.findViewById(R.id.cart_product_name);
            productDescriptionTextView = itemView.findViewById(R.id.cart_product_description);
            productPriceTextView = itemView.findViewById(R.id.cart_product_price);
            btnRemove = itemView.findViewById(R.id.btn_remove); // Ensure this ID matches your layout
        }
    }
}
