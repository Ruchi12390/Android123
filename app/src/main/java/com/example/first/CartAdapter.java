package com.example.first;

import android.content.Intent;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private ArrayList<Product1> cartItems;
    private int buyerId;
    private DatabaseHelper databaseHelper;

    public CartAdapter(ArrayList<Product1> cartItems, int buyerId, DatabaseHelper dbHelper) {
        this.cartItems = cartItems;
        this.buyerId = buyerId;
        this.databaseHelper = dbHelper;
    }

    // Method to remove an item from the cart and update the adapter
    public void removeItem(int position) {
        if (position >= 0 && position < cartItems.size()) {
            cartItems.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItems.size());
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
        Product1 product = cartItems.get(position);
        holder.productNameTextView.setText(product.getName());
        holder.productDescriptionTextView.setText(product.getDescription());
        holder.productPriceTextView.setText("₹" + product.getPrice());


        // Load product image using Glide
        Glide.with(holder.itemView.getContext())
                .load(product.getImageUri())
                .placeholder(R.drawable.img)
                .error(R.drawable.img)
                .into(holder.productImageView);

        // Set the remove button click listener
        holder.btnRemove.setOnClickListener(v -> {
            long result = databaseHelper.removeProductFromCart1(buyerId, product.getName());
            if (result > 0) {
                removeItem(position);
            } else {
                Toast.makeText(holder.itemView.getContext(), "Error removing item", Toast.LENGTH_SHORT).show();
            }
        });


        // Set the Pay Now button click listener to navigate to PlaceOrderActivity
        holder.btnPlaceOrder.setOnClickListener(v -> {
            String priceString = product.getPrice(); // Assuming this returns a String
            double price = 0.0;

            try {
                price = Double.parseDouble(priceString);  // Convert String to double
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Toast.makeText(holder.itemView.getContext(), "Invalid price format", Toast.LENGTH_SHORT).show();
            }

            if (price > 0) {
                Intent intent = new Intent(holder.itemView.getContext(), PlaceOrderActivity.class);
                intent.putExtra("product_id", product.getId());
                intent.putExtra("product_price", price);  // Pass the price as double
                holder.itemView.getContext().startActivity(intent);
            } else {
                Toast.makeText(holder.itemView.getContext(), "Invalid price", Toast.LENGTH_SHORT).show();
            }
        });




    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    // ViewHolder class to represent each cart item
    public static class CartViewHolder extends RecyclerView.ViewHolder {

        TextView productNameTextView, productDescriptionTextView, productPriceTextView;
        ImageView productImageView;
        Button btnRemove, btnPlaceOrder;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.cart_product_image);
            productNameTextView = itemView.findViewById(R.id.cart_product_name);
            productDescriptionTextView = itemView.findViewById(R.id.cart_product_description);
            productPriceTextView = itemView.findViewById(R.id.cart_product_price);
            btnRemove = itemView.findViewById(R.id.btn_remove);
            btnPlaceOrder = itemView.findViewById(R.id.btn_placeOrder);
        }
    }
}
